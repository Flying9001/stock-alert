package com.ljq.stock.alert.common.constant;

/**
 * @Description: 预警消息常量类
 * @Author: junqiang.lu
 * @Date: 2021/4/20
 */
public class MessageConst {

    private MessageConst() {
    }

    /**
     * 消息发送状态
     * 0: 发送失败
     * 1: 发送成功
     * 2: 未发送
     */
    public static final int MESSAGE_SEND_FAIL = 0;
    public static final int MESSAGE_SEND_SUCCESS = 1;
    public static final int MESSAGE_SEND_NOT = 2;

    /**
     * 消息提醒类型
     * 11: 最低股价提醒
     * 12: 最高股价提醒
     * 21: 最大跌幅提醒
     * 22: 最大涨幅提醒
     */
    public static final int ALERT_TYPE_PRICE_MIN = 11;
    public static final int ALERT_TYPE_PRICE_MAX = 12;
    public static final int ALERT_TYPE_INCREASE_PER_MIN = 21;
    public static final int ALERT_TYPE_INCREASE_PER_MAX = 22;

    /**
     * 待发送预警消息缓存前缀
     */
    public static final String CACHE_KEY_ALERT_MESSAGE_TO_SEND = "ALERT_MESSAGE_TO_SEND_";

    /**
     * 默认待发送预警消息缓存时间
     */
    public static final long DEFAULT_TIME_ALERT_MESSAGE_MQ = 3600L;


}
