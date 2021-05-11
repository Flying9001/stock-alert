package com.ljq.stock.alert.common.component;

import com.ljq.stock.alert.common.config.RabbitMqConfig;
import com.ljq.stock.alert.common.constant.MessageConst;
import com.ljq.stock.alert.model.entity.AlertMessageEntity;
import com.ljq.stock.alert.service.AlertMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private AlertMessageService alertMessageService;
    @Autowired
    private MailClient mailClient;



    /**
     * 接收队列消息
     *
     * @param alertMessageList
     */
    @RabbitListener(queues = RabbitMqConfig.QUEUE_ALERT_MESSAGE)
    public void receive(List<AlertMessageEntity> alertMessageList) {
        log.info("Received  {} ", alertMessageList);
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
        } catch (MessagingException e) {
            log.error("Mail send error,{}", e);
            // 更新消息状态
            alertMessage.setEmailSend(MessageConst.MESSAGE_SEND_FAIL);
            alertMessageService.updateById(alertMessage);
        }
        // 更新消息状态
        alertMessage.setEmailSend(MessageConst.MESSAGE_SEND_SUCCESS);
        alertMessageService.updateById(alertMessage);
    }

}
