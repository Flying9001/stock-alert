package com.ljq.stock.alert.service.component;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ljq.stock.alert.common.component.MailClient;
import com.ljq.stock.alert.common.component.PushPlusClient;
import com.ljq.stock.alert.common.component.RedisUtil;
import com.ljq.stock.alert.common.component.WxPusherClient;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private MessagePushResultDao pushResultDao;
    @Resource
    private WxPusherClient wxPusherClient;

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
                    PushPlusClient.PushParam pushParam = new PushPlusClient.PushParam();
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
        // 查询用户支持的推送方式
        List<UserPushTypeEntity> userPushTypeList = userPushTypeService.list(Wrappers
                .lambdaQuery(UserPushTypeEntity.class)
                .eq(UserPushTypeEntity::getUserId, alertMessage.getUserId())
                .eq(UserPushTypeEntity::getEnable, EnableEnum.ENABLE.getCode()));
        Map<Integer, String> userPushTypeMap = userPushTypeList.stream()
                .collect(Collectors.toMap(UserPushTypeEntity::getPushType, UserPushTypeEntity::getReceiveAddress));
        List<Integer> pushTypeList;
        // 判断是是首次推送还是重试消息
        if (Objects.isNull(alertMessage.getPushTotal()) || alertMessage.getPushTotal() < 1) {
            // 首次推送
            // 设置推送总次数
            alertMessage.setPushTotal(userPushTypeList.size());
            alertMessageService.updateById(alertMessage);
            pushTypeList = userPushTypeList.stream().map(UserPushTypeEntity::getPushType).collect(Collectors.toList());
        } else {
            // 重试推送
            // 查询推送失败的消息
            List<MessagePushResultEntity> pushResultList = pushResultDao.selectList(Wrappers
                    .lambdaQuery(MessagePushResultEntity.class)
                    .eq(MessagePushResultEntity::getMessageId, alertMessage.getId())
                    .eq(MessagePushResultEntity::getPushResult, MessageConst.MESSAGE_SEND_FAIL));
            pushTypeList = pushResultList.stream().map(MessagePushResultEntity::getPushType).collect(Collectors.toList());
        }
        try {
            // 根据推送类型推送消息
            for (Integer pushType : pushTypeList) {
                switch (pushType) {
                    case UserPushConst.USER_PUSH_TYPE_SMS:
                        // TODO 预留短信通知
                        continue;
                    case UserPushConst.USER_PUSH_TYPE_EMAIL:
                        sendByEmail(alertMessage, userPushTypeMap.get(pushType));
                        continue;
                    case UserPushConst.USER_PUSH_TYPE_PUSHPLUS_WECHAT_PUBLIC:
                        sendByPushPlusWechatPublic(alertMessage, userPushTypeMap.get(pushType));
                        continue;
                    case UserPushConst.USER_PUSH_TYPE_WXPUSHER:
                        sendByWxPusher(alertMessage, userPushTypeMap.get(pushType));
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
            pushResultDao.update(pushResultError, wrapper);
            // 释放消息消费资源
            String cacheKey = CacheKeyUtil.create(MessageConst.CACHE_KEY_ALERT_MESSAGE_TO_SEND,
                    String.valueOf(alertMessage.getId()), null);
            redisUtil.remove(cacheKey);
        }
    }

    /**
     * 通过邮件方式推送
     *
     * @param alertMessage
     * @param receiveAddress
     */
    private void sendByEmail(AlertMessageEntity alertMessage, String receiveAddress) throws MessagingException {
        // 设置推送结果
        MessagePushResultEntity pushResult = new MessagePushResultEntity();
        pushResult.setMessageId(alertMessage.getId());
        pushResult.setPushType(UserPushConst.USER_PUSH_TYPE_EMAIL);
        // 推送消息
        mailClient.sendMail(receiveAddress, alertMessage.getTitle(), alertMessage.getContent());
        // 更新推送结果
        LambdaQueryWrapper<MessagePushResultEntity> wrapper = Wrappers.lambdaQuery(MessagePushResultEntity.class);
        wrapper.eq(MessagePushResultEntity::getMessageId, alertMessage.getId())
                .eq(MessagePushResultEntity::getPushType, UserPushConst.USER_PUSH_TYPE_PUSHPLUS_WECHAT_PUBLIC);
        MessagePushResultEntity pushResultDb = pushResultDao.selectOne(wrapper);
        pushResult.setPushResult(MessageConst.MESSAGE_SEND_SUCCESS);
        if (Objects.isNull(pushResultDb)) {
            pushResult.setRetryTime(0);
            pushResultDao.insert(pushResult);
        } else {
            pushResultDao.update(pushResult, wrapper);
        }
        alertMessage.setPushCount(alertMessage.getPushCount() + 1);
        alertMessageService.updateById(alertMessage);
    }

    /**
     * 通过 pushPlus 微信公众号消息推送
     *
     * @param alertMessage
     * @param receiveAddress
     */
    private void sendByPushPlusWechatPublic(AlertMessageEntity alertMessage, String receiveAddress) {
        // 设置推送结果
        MessagePushResultEntity pushResult = new MessagePushResultEntity();
        pushResult.setMessageId(alertMessage.getId());
        pushResult.setPushType(UserPushConst.USER_PUSH_TYPE_PUSHPLUS_WECHAT_PUBLIC);
        // 推送消息
        PushPlusClient.PushParam pushParam = new PushPlusClient.PushParam();
        pushParam.setToken(receiveAddress);
        pushParam.setChannel(PushPlusChannelEnum.WECHAT_PUBLIC.getChannel());
        pushParam.setTitle(alertMessage.getTitle());
        pushParam.setContent(alertMessage.getContent());
        String pushRecord = pushPlusClient.push(pushParam);
        // 推送结果
        int realPushResult = StrUtil.isBlank(pushRecord) ? MessageConst.MESSAGE_SEND_FAIL
                : MessageConst.MESSAGE_SEND_SUCCESS;
        // 更新推送结果
        LambdaQueryWrapper<MessagePushResultEntity> wrapper = Wrappers.lambdaQuery(MessagePushResultEntity.class);
        wrapper.eq(MessagePushResultEntity::getMessageId, alertMessage.getId())
                .eq(MessagePushResultEntity::getPushType, UserPushConst.USER_PUSH_TYPE_PUSHPLUS_WECHAT_PUBLIC);
        MessagePushResultEntity pushResultDb = pushResultDao.selectOne(wrapper);
        pushResult.setPushResult(realPushResult);
        pushResult.setPushRecord(pushRecord);
        if (Objects.isNull(pushResultDb)) {
            pushResult.setRetryTime(0);
            pushResultDao.insert(pushResult);
        } else {
            pushResultDao.update(pushResult, wrapper);
        }
        // 只有推送成功才增加消息推送次数
        if (Objects.equals(realPushResult, MessageConst.MESSAGE_SEND_SUCCESS)) {
            alertMessage.setPushCount(alertMessage.getPushCount() + 1);
            alertMessageService.updateById(alertMessage);
        }
    }

    /**
     * 通过 WxPusher 微信公众号消息推送
     *
     * @param alertMessage
     * @param receiveAddress
     */
    private void sendByWxPusher(AlertMessageEntity alertMessage, String receiveAddress) {
        // 设置推送结果
        MessagePushResultEntity pushResult = new MessagePushResultEntity();
        pushResult.setMessageId(alertMessage.getId());
        pushResult.setPushType(UserPushConst.USER_PUSH_TYPE_WXPUSHER);
        // 推送消息
        WxPusherClient.PushParam pushParam = new WxPusherClient.PushParam();
        pushParam.setSummary(alertMessage.getTitle());
        pushParam.setContentType(1);
        pushParam.setContent(alertMessage.getContent());
        pushParam.setUids(Collections.singletonList(receiveAddress));
        String pushRecord = wxPusherClient.push(pushParam);
        // 推送结果
        int realPushResult = StrUtil.isBlank(pushRecord) ? MessageConst.MESSAGE_SEND_FAIL
                : MessageConst.MESSAGE_SEND_SUCCESS;
        // 更新推送结果
        LambdaQueryWrapper<MessagePushResultEntity> wrapper = Wrappers.lambdaQuery(MessagePushResultEntity.class);
        wrapper.eq(MessagePushResultEntity::getMessageId, alertMessage.getId())
                .eq(MessagePushResultEntity::getPushType, UserPushConst.USER_PUSH_TYPE_WXPUSHER);
        MessagePushResultEntity pushResultDb = pushResultDao.selectOne(wrapper);
        pushResult.setPushResult(realPushResult);
        pushResult.setPushRecord(pushRecord);
        if (Objects.isNull(pushResultDb)) {
            pushResult.setRetryTime(0);
            pushResultDao.insert(pushResult);
        } else {
            pushResultDao.update(pushResult, wrapper);
        }
        // 只有推送成功才增加消息推送次数
        if (Objects.equals(realPushResult, MessageConst.MESSAGE_SEND_SUCCESS)) {
            alertMessage.setPushCount(alertMessage.getPushCount() + 1);
            alertMessageService.updateById(alertMessage);
        }
    }


}
