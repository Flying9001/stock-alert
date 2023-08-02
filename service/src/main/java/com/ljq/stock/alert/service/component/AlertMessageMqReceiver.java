package com.ljq.stock.alert.service.component;

import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ljq.stock.alert.common.component.MailClient;
import com.ljq.stock.alert.common.component.RedisUtil;
import com.ljq.stock.alert.common.config.RabbitMqConfig;
import com.ljq.stock.alert.common.constant.EnableEnum;
import com.ljq.stock.alert.common.constant.MessageConst;
import com.ljq.stock.alert.common.constant.UserPushConst;
import com.ljq.stock.alert.common.util.CacheKeyUtil;
import com.ljq.stock.alert.model.entity.AlertMessageEntity;
import com.ljq.stock.alert.model.entity.UserPushTypeEntity;
import com.ljq.stock.alert.service.AlertMessageService;
import com.ljq.stock.alert.service.UserPushTypeService;
import com.ljq.stock.alert.service.task.MessageMailTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: RabbitMQ 消息队列消费者
 * @Author: junqiang.lu
 * @Date: 2019/1/21
 */
@Slf4j
@Service
public class AlertMessageMqReceiver {

    @Autowired
    private AlertMessageService alertMessageService;
    @Autowired
    private MailClient mailClient;
    @Resource
    private JavaMailSender mailSender;
    @Autowired
    private RedisUtil redisUtil;
    @Resource
    private UserPushTypeService userPushTypeService;

    /**
     * 发件人邮箱
     */
    @Value("${spring.mail.username}")
    private String from;



    /**
     * 接收预警消息队列消息
     *
     * @param alertMessageList
     */
    @RabbitListener(queues = RabbitMqConfig.QUEUE_ALERT_MESSAGE)
    public void receiveAlertMessage(List<AlertMessageEntity> alertMessageList) {
        alertMessageList.stream().forEach(message -> {
            String cacheKey = CacheKeyUtil.create(MessageConst.CACHE_KEY_ALERT_MESSAGE_TO_SEND,
                    String.valueOf(message.getId()), null);
            if (redisUtil.exists(cacheKey)) {
                return;
            }
            redisUtil.set(cacheKey, message.getId(), MessageConst.DEFAULT_TIME_ALERT_MESSAGE_MQ);
            sendAndUpdateMessage(message);
        });
    }

    /**
     * 接收股价报告消息队列消息
     *
     * @param alertMessageList
     */
    @RabbitListener(queues = RabbitMqConfig.QUEUE_REPORT_MESSAGE)
    public void receiveReportMessage(List<AlertMessageEntity> alertMessageList) {
        alertMessageList.stream().forEach(message -> {
            String cacheKey = CacheKeyUtil.create(MessageConst.CACHE_KEY_ALERT_MESSAGE_TO_SEND,
                    String.valueOf(message.getId()), null);
            if (redisUtil.exists(cacheKey)) {
                return;
            }
            redisUtil.set(cacheKey, message.getId(), MessageConst.DEFAULT_TIME_ALERT_MESSAGE_MQ);
            // 发送邮件
            try {
                mailClient.sendMail(message.getReceiveAddress(), message.getTitle(), message.getContent());
            } catch (Exception e) {
                log.error("report message email send error", e);
            }
        });
    }

    /**
     * 接收用户操作队列消息
     *
     * @param alertMessage
     */
    @RabbitListener(queues = {RabbitMqConfig.QUEUE_USER_OPERATE})
    public void receiveUserOperate(AlertMessageEntity alertMessage) {
        try {
            log.info("userOperateMessage:id={},title={}", alertMessage.getId(), alertMessage.getTitle());
            switch (alertMessage.getPushType()) {
                case UserPushConst.USER_PUSH_TYPE_EMAIL:
                    mailClient.sendMail(alertMessage.getReceiveAddress(), alertMessage.getTitle(), alertMessage.getContent());
                    break;
                case UserPushConst.USER_PUSH_TYPE_PUSHPLUS_WECHAT_PUBLIC:
                    // TODO pushplus 微信公众号推送

                case UserPushConst.USER_PUSH_TYPE_SMS:
                    // TODO 预留短信通知
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            log.error("user operate send error", e);
        }
    }

    /**
     * 发送并更新预警消息
     *
     * @param alertMessage
     */
    private void sendAndUpdateMessage(AlertMessageEntity alertMessage) {
        log.info("alertMessage:id={},title={}", alertMessage.getId(), alertMessage.getTitle());
        List<UserPushTypeEntity> pushTypeList = userPushTypeService.list(Wrappers.lambdaQuery(UserPushTypeEntity.class)
                .eq(UserPushTypeEntity::getUserId, alertMessage.getUserId())
                .eq(UserPushTypeEntity::getEnable, EnableEnum.ENABLE.getCode()));
        try {
            // 根据推送类型推送消息
            for (UserPushTypeEntity pushType : pushTypeList) {
                switch (pushType.getPushType()) {
                    case UserPushConst.USER_PUSH_TYPE_SMS:
                        // TODO 预留短信通知
                        alertMessage.setPushType(UserPushConst.USER_PUSH_TYPE_SMS);
                        continue;
                    case UserPushConst.USER_PUSH_TYPE_EMAIL:
                        alertMessage.setPushType(UserPushConst.USER_PUSH_TYPE_EMAIL);
                        mailClient.sendMail(pushType.getReceiveAddress(), alertMessage.getTitle(),
                                alertMessage.getContent());
                        alertMessage.setPushResult(MessageConst.MESSAGE_SEND_SUCCESS);
                        continue;
                    case UserPushConst.USER_PUSH_TYPE_PUSHPLUS_WECHAT_PUBLIC:
                        // TODO pushplus 微信公众号推送
                        alertMessage.setPushType(UserPushConst.USER_PUSH_TYPE_PUSHPLUS_WECHAT_PUBLIC);

                        continue;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            log.error("alert message send error", e);
            // 更新消息状态
            alertMessage.setPushResult(MessageConst.MESSAGE_SEND_FAIL);
            alertMessage.setRetryTime(alertMessage.getRetryTime() + 1);
            alertMessageService.updateById(alertMessage);
            String cacheKey = CacheKeyUtil.create(MessageConst.CACHE_KEY_ALERT_MESSAGE_TO_SEND,
                    String.valueOf(alertMessage.getId()), null);
            redisUtil.remove(cacheKey);
            return;
        }
        // 更新消息状态
        alertMessageService.updateById(alertMessage);
    }

    /**
     * 批量发送并更新预警消息
     *
     * @param alertMessageList
     */
    private void sendAndUpdateMessageBatch(List<AlertMessageEntity> alertMessageList) {
        for (int i = 0; i < alertMessageList.size(); i++) {
            alertMessageList.get(i).setSenderEmail(from);
            try {
                ThreadUtil.execAsync(new MessageMailTask(mailSender, alertMessageList.get(i)));
                alertMessageList.get(i).setEmailSend(MessageConst.MESSAGE_SEND_SUCCESS);
            } catch (Exception e) {
                log.error("Mail send error", e);
                // 更新消息状态
                alertMessageList.get(i).setEmailSend(MessageConst.MESSAGE_SEND_FAIL);
                alertMessageList.get(i).setRetryTime(alertMessageList.get(i).getRetryTime() + 1);
                String cacheKey = CacheKeyUtil.create(MessageConst.CACHE_KEY_ALERT_MESSAGE_TO_SEND,
                        String.valueOf(alertMessageList.get(i).getId()), null);
                redisUtil.remove(cacheKey);
            }
        }
        alertMessageService.updateBatchById(alertMessageList);
    }


}
