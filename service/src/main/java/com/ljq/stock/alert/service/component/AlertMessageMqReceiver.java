package com.ljq.stock.alert.service.component;

import cn.hutool.core.thread.ThreadUtil;
import com.ljq.stock.alert.common.component.MailClient;
import com.ljq.stock.alert.common.config.RabbitMqConfig;
import com.ljq.stock.alert.common.constant.MessageConst;
import com.ljq.stock.alert.model.entity.AlertMessageEntity;
import com.ljq.stock.alert.service.AlertMessageService;
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
    /**
     * 发件人邮箱
     */
    @Value("${spring.mail.username}")
    private String from;



    /**
     * 接收队列消息
     *
     * @param alertMessageList
     */
    @RabbitListener(queues = RabbitMqConfig.QUEUE_ALERT_MESSAGE)
    public void receive(List<AlertMessageEntity> alertMessageList) {
        alertMessageList.stream().forEach(this::sendAndUpdateMessage);
    }

    /**
     * 发送并更新预警消息
     *
     * @param alertMessage
     */
    private void sendAndUpdateMessage(AlertMessageEntity alertMessage) {
        try {
            mailClient.sendMail(alertMessage.getEmail(), alertMessage.getTitle(), alertMessage.getContent());
        } catch (Exception e) {
            log.error("Mail send error", e);
            // 更新消息状态
            alertMessage.setEmailSend(MessageConst.MESSAGE_SEND_FAIL);
            alertMessage.setRetryTime(alertMessage.getRetryTime() + 1);
            alertMessageService.updateById(alertMessage);
            return;
        }
        // 更新消息状态
        alertMessage.setEmailSend(MessageConst.MESSAGE_SEND_SUCCESS);
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
                log.error("Mail send error,{}", e);
                // 更新消息状态
                alertMessageList.get(i).setEmailSend(MessageConst.MESSAGE_SEND_FAIL);
            }
        }
        alertMessageService.updateBatchById(alertMessageList);
    }


}
