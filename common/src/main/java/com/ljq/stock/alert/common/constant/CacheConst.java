package com.ljq.stock.alert.common.constant;

/**
 * @Description: 缓存常量
 * @Author: junqiang.lu
 * @Date: 2021/11/4
 */
public class CacheConst {

    private CacheConst(){
    }

    /**
     * 所有股票源数据
     */
    public static final String CACHE_KEY_STOCK_SOURCE_ALL = "STOCK_SOURCE_ALL";

    /**
     * 待发送预警消息缓存前缀
     */
    public static final String CACHE_KEY_ALERT_MESSAGE_TO_SEND = "ALERT_MESSAGE_TO_SEND_";

    /**
     * 默认待发送预警消息缓存时间
     */
    public static final long DEFAULT_TIME_ALERT_MESSAGE_MQ = 3600L;


}
