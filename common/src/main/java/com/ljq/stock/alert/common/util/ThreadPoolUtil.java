package com.ljq.stock.alert.common.util;

import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 线程池工具类
 * @Author: junqiang.lu
 * @Date: 2023/11/30
 */
@Slf4j
public class ThreadPoolUtil {

    /**
     * 默认核心线程数
     */
    private static final int DEFAULT_CORE_SIZE = 4;
    /**
     * 默认最大线程数
     */
    private static final int DEFAULT_MAX_SIZE = 8;

    /**
     * 默认等待时间(单位:秒)
     */
    private static final long DEFAULT_ACTIVE_TIME = 120;

    /**
     * 默认队列大小
     */
    private static final int DEFAULT_QUEUE_SIZE = 1000;


    private ThreadPoolUtil() {
    }

    private static volatile ThreadPoolExecutor commonThreadPoolExecutor;

    private static volatile ScheduledThreadPoolExecutor scheduleThreadPoolExecutor;

    /**
     * 获取公共线程池
     *
     * @param poolName
     * @return
     */
    public static ThreadPoolExecutor getCommonPool(String poolName) {
        if (Objects.isNull(commonThreadPoolExecutor) || commonThreadPoolExecutor.isShutdown()) {
            synchronized (ThreadPoolUtil.class) {
                if (Objects.isNull(commonThreadPoolExecutor) || commonThreadPoolExecutor.isShutdown()) {
                    commonThreadPoolExecutor = new ThreadPoolExecutor(DEFAULT_CORE_SIZE, DEFAULT_MAX_SIZE,
                            DEFAULT_ACTIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<>(DEFAULT_QUEUE_SIZE),
                            new DefaultThreadFactory(poolName),
                            new ThreadPoolExecutor.DiscardOldestPolicy());
                }
            }
        }
        return commonThreadPoolExecutor;
    }

    /**
     * 获取定时任务线程池
     *
     * @param poolName
     * @return
     */
    public static ScheduledThreadPoolExecutor getSchedulePool(String poolName) {
        if (Objects.isNull(scheduleThreadPoolExecutor) || scheduleThreadPoolExecutor.isShutdown()) {
            synchronized (ThreadPoolUtil.class) {
                if (Objects.isNull(scheduleThreadPoolExecutor) || scheduleThreadPoolExecutor.isShutdown()) {
                    scheduleThreadPoolExecutor = new ScheduledThreadPoolExecutor(DEFAULT_CORE_SIZE,
                            new DefaultThreadFactory(poolName), new ThreadPoolExecutor.DiscardOldestPolicy());
                }
            }
        }
        return scheduleThreadPoolExecutor;
    }

    /**
     * 停止公共线程池
     */
    public static void shutdownCommonPool() {
        scheduleThreadPoolExecutor.shutdown();
    }

    /**
     * 停止定时任务线程池
     */
    public static void shutdownSchedulePool() {
        scheduleThreadPoolExecutor.shutdown();
    }



}
