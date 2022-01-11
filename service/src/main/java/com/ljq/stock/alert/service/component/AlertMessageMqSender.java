package com.ljq.stock.alert.service.component;

import com.ljq.stock.alert.common.config.RabbitMqConfig;
import com.ljq.stock.alert.model.entity.AlertMessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: rabbitMQ 消息发送者
 * @Author: junqiang.lu
 * @Date: 2019/1/21
 */
@Slf4j
@Service
public class AlertMessageMqSender {


    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * 批量发送预警消息队列消息
     *
     * @param alertMessageList
     */
    public void sendBatchAlertMessage(List<AlertMessageEntity> alertMessageList) {
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_DIRECT,RabbitMqConfig.QUEUE_ALERT_MESSAGE,
                alertMessageList);
    }

    /**
     * 发送用户操作队列消息
     *
     * @param alertMessage
     */
    public void sendUserOperate(AlertMessageEntity alertMessage) {
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_DIRECT, RabbitMqConfig.QUEUE_USER_OPERATE, alertMessage);
    }



}
