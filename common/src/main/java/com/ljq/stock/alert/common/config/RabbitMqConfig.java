package com.ljq.stock.alert.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: rabbitMq 配置信息
 * @Author: junqiang.lu
 * @Date: 2019/1/21
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 消息队列名称
     * RABBITMQ_STOCK_ALERT_MESSAGE: 预警消息
     * RABBITMQ_STOCK_REPORT_MESSAGE: 股票周报
     * RABBITMQ_STOCK_USER_CHECK: 用户验证码操作
     */
    public static final String QUEUE_ALERT_MESSAGE = "RABBITMQ_STOCK_ALERT_MESSAGE";
    public static final String QUEUE_REPORT_MESSAGE = "RABBITMQ_STOCK_REPORT_MESSAGE";
    public static final String QUEUE_USER_OPERATE = "RABBITMQ_STOCK_USER_CHECK";

    /**
     * 直连交换机名称
     */
    public static final String EXCHANGE_DIRECT = "RABBITMQ_EXCHANGE_DIRECT";


    /**
     * 预警消息队列
     *
     * @return
     */
    @Bean(value = "alertMessageQueue")
    public Queue alertMessageQueue(){
        return new Queue(QUEUE_ALERT_MESSAGE);
    }

    /**
     * 股价报告消息队列
     *
     * @return
     */
    @Bean(value = "reportMessageQueue")
    public Queue reportMessageQueue() {
        return new Queue(QUEUE_REPORT_MESSAGE);
    }

    /**
     * 用户操作队列
     *
     * @return
     */
    @Bean(value = "userOperateQueue")
    public Queue userOperateQueue() {
        return new Queue(QUEUE_USER_OPERATE);
    }

    /**
     * 直连交换机
     *
     * @return
     */
    @Bean(value = "directExchange")
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_DIRECT, false, true);
    }

    /**
     * 绑定直连交换机与预警消息队列
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding bindingDirectExchangeAlertMessage(@Qualifier("alertMessageQueue") Queue queue,
                                                     @Qualifier("directExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).withQueueName();
    }

    /**
     * 绑定直连交换机与股价报告消息队列
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding bindingDirectExchangeReportMessage(@Qualifier("reportMessageQueue") Queue queue,
                                                     @Qualifier("directExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).withQueueName();
    }

    /**
     * 绑定直连交换机与用户操作队列
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding bindingDirectExchangeUserOperate(@Qualifier("userOperateQueue") Queue queue,
                                                     @Qualifier("directExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).withQueueName();
    }


}
