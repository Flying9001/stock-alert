package com.ljq.stock.alert.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description: 定时任务线程池配置
 * @Author: junqiang.lu
 * @Date: 2023/12/27
 */
@Configuration
public class TaskExecutorConfig {

    public static final String STOCK_ALERT_SCHEDULE_NAME = "stockAlertScheduledExecutor";

    /**
     * 定时任务异步线程池
     *
     * @return
     */
    @Bean(name = STOCK_ALERT_SCHEDULE_NAME)
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(2);
        taskExecutor.setMaxPoolSize(4);
        taskExecutor.setKeepAliveSeconds(120);
        taskExecutor.setQueueCapacity(300);
        taskExecutor.setThreadNamePrefix("stock-alert-executor-");
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        return taskExecutor;
    }



}
