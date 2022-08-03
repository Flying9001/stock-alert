package com.ljq.stock.alert.model.param.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 微信小程序消息推送请求参数
 * @Author: junqiang.lu
 * @Date: 2022/8/3
 */
@Data
public class WechatMiniMsgParam implements Serializable {

    private static final long serialVersionUID = -3957104767599936275L;

    /**
     * 签名
     */
    private String signature;

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     * 随机数
     */
    private String nonce;

    /**
     * 随机字符串
     */
    private String echostr;

    /**
     * 消息内容
     */
    private String content;

}
