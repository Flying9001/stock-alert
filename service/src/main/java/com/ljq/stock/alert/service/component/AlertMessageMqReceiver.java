package com.ljq.stock.alert.service.component;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ljq.stock.alert.common.component.MailClient;
import com.ljq.stock.alert.common.component.PushPlusClient;
import com.ljq.stock.alert.common.component.RedisUtil;
import com.ljq.stock.alert.common.config.RabbitMqConfig;
import com.ljq.stock.alert.common.constant.EnableEnum;
import com.ljq.stock.alert.common.constant.MessageConst;
import com.ljq.stock.alert.common.constant.PushPlusChannelEnum;
import com.ljq.stock.alert.common.constant.UserPushConst;
import com.ljq.stock.alert.common.util.CacheKeyUtil;
import com.ljq.stock.alert.dao.MessagePushResultDao;
import com.ljq.stock.alert.model.entity.AlertMessageEntity;
import com.ljq.stock.alert.model.entity.MessagePushResultEntity;
import com.ljq.stock.alert.model.entity.UserPushTypeEntity;
import com.ljq.stock.alert.service.AlertMessageService;
import com.ljq.stock.alert.service.UserPushTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.List;

/**
 * @Description: RabbitMQ 消息队列消费者
 * @Author: junqiang.lu
 * @Date: 2019/1/21
 */
@Slf4j
@Service
public class AlertMessageMqReceiver {

    @Resource
    private AlertMessageService alertMessageService;
    @Resource
    private MailClient mailClient;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private UserPushTypeService userPushTypeService;
    @Resource
    private PushPlusClient pushPlusClient;
    @Resource
    private MessagePushResultDao messagePushResultDao;

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
                    PushPlusClient.PushPlusPushParam pushParam = new PushPlusClient.PushPlusPushParam();
                    pushParam.setToken(alertMessage.getReceiveAddress());
                    pushParam.setChannel(PushPlusChannelEnum.WECHAT_PUBLIC.getChannel());
                    pushParam.setTitle(alertMessage.getTitle());
                    pushParam.setContent(alertMessage.getContent());
                    pushPlusClient.push(pushParam);
                    break;
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
        // 设置消息总共推送次数
        alertMessage.setPushTotal(pushTypeList.size());
        alertMessageService.updateById(alertMessage);
        try {
            // 根据推送类型推送消息
            for (UserPushTypeEntity pushType : pushTypeList) {
                switch (pushType.getPushType()) {
                    case UserPushConst.USER_PUSH_TYPE_SMS:
                        // TODO 预留短信通知
                        continue;
                    case UserPushConst.USER_PUSH_TYPE_EMAIL:
                        // 设置推送结果
                        MessagePushResultEntity pushResultEmail = new MessagePushResultEntity();
                        pushResultEmail.setMessageId(alertMessage.getId());
                        pushResultEmail.setPushType(UserPushConst.USER_PUSH_TYPE_EMAIL);
                        pushResultEmail.setRetryTime(0);
                        // 推送消息
                        mailClient.sendMail(pushType.getReceiveAddress(), alertMessage.getTitle(),
                                alertMessage.getContent());
                        // 更新推送结果
                        pushResultEmail.setPushResult(MessageConst.MESSAGE_SEND_SUCCESS);
                        messagePushResultDao.insert(pushResultEmail);
                        alertMessage.setPushCount(alertMessage.getPushCount() + 1);
                        alertMessageService.updateById(alertMessage);
                        continue;
                    case UserPushConst.USER_PUSH_TYPE_PUSHPLUS_WECHAT_PUBLIC:
                        // 设置推送结果
                        MessagePushResultEntity pushResultPushPlus = new MessagePushResultEntity();
                        pushResultPushPlus.setMessageId(alertMessage.getId());
                        pushResultPushPlus.setPushType(UserPushConst.USER_PUSH_TYPE_PUSHPLUS_WECHAT_PUBLIC);
                        pushResultPushPlus.setRetryTime(0);
                        // 推送消息
                        PushPlusClient.PushPlusPushParam pushParam = new PushPlusClient.PushPlusPushParam();
                        pushParam.setToken(pushType.getReceiveAddress());
                        pushParam.setChannel(PushPlusChannelEnum.WECHAT_PUBLIC.getChannel());
                        pushParam.setTitle(alertMessage.getTitle());
                        pushParam.setContent(alertMessage.getContent());
                        String pushRecord = pushPlusClient.push(pushParam);
                        // 更新推送结果
                        pushResultPushPlus.setPushResult(MessageConst.MESSAGE_SEND_SUCCESS);
                        pushResultPushPlus.setPushRecord(pushRecord);
                        messagePushResultDao.insert(pushResultPushPlus);
                        alertMessage.setPushCount(alertMessage.getPushCount() + 1);
                        alertMessageService.updateById(alertMessage);
                        continue;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            log.error("alert message send error", e);
            // 更新消息状态
            MessagePushResultEntity pushResultError = new MessagePushResultEntity();
            pushResultError.setPushResult(MessageConst.MESSAGE_SEND_FAIL);
            pushResultError.setRetryTime(pushResultError.getRetryTime() + 1);
            LambdaUpdateWrapper<MessagePushResultEntity> wrapper = Wrappers.lambdaUpdate(MessagePushResultEntity.class);
            wrapper.eq(MessagePushResultEntity::getMessageId, alertMessage.getId());
            // 根据异常类型判定是什么方式推送失败
            if (MessagingException.class.isAssignableFrom(e.getClass())) {
                wrapper.eq(MessagePushResultEntity::getPushType, UserPushConst.USER_PUSH_TYPE_EMAIL);
            } else {
                wrapper.eq(MessagePushResultEntity::getPushType, UserPushConst.USER_PUSH_TYPE_PUSHPLUS_WECHAT_PUBLIC);
            }
            messagePushResultDao.update(pushResultError, wrapper);
            // 释放消息消费资源
            String cacheKey = CacheKeyUtil.create(MessageConst.CACHE_KEY_ALERT_MESSAGE_TO_SEND,
                    String.valueOf(alertMessage.getId()), null);
            redisUtil.remove(cacheKey);
        }
    }



}
