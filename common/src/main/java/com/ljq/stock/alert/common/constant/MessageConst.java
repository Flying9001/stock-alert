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
     * 1: 发送成功
     * 2: 发送失败
     * 3: 未发送
     */
    public static final int MESSAGE_SEND_SUCCESS = 1;
    public static final int MESSAGE_SEND_FAIL = 2;
    public static final int MESSAGE_SEND_NOT = 3;


}
