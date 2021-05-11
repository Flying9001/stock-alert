package com.ljq.stock.alert.common.component;

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
     * 批量发送队列消息
     *
     * @param alertMessageList
     */
    public void sendBatch(List<AlertMessageEntity> alertMessageList) {
        log.info("批量推送,{}", alertMessageList);
        rabbitTemplate.convertAndSend(RabbitMqConfig.QUEUE_ALERT_MESSAGE, alertMessageList);
    }



}
