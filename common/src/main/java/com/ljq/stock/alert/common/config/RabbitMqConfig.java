package com.ljq.stock.alert.common.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: rabbitMq 配置信息
 * @Author: junqiang.lu
 * @Date: 2019/1/21
 */
@Configuration
public class RabbitMqConfig {

    public static final String QUEUE_ALERT_MESSAGE = "rabbitmq_stock_alert";

    @Bean
    public Queue queue(){
        return new Queue(QUEUE_ALERT_MESSAGE);
    }


}
