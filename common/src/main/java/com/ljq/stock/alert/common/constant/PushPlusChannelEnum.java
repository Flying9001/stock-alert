package com.ljq.stock.alert.common.constant;

import lombok.Getter;
import lombok.ToString;

/**
 * @Description: PushPlus 推送通道
 * @Author: junqiang.lu
 * @Date: 2023/7/26
 */
@Getter
@ToString
public enum PushPlusChannelEnum {

    /**
     * 推送通道
     */
    WECHAT_PUBLIC("wechat", "微信公众号(默认推送通道)"),
    WEBHOOK("webhook", "第三方 web"),
    MAIL("mail", "邮件"),



    ;

    /**
     * 推送通道
     */
    private String channel;

    /**
     * 说明
     */
    private String description;


    PushPlusChannelEnum(String channel, String description) {
        this.channel = channel;
        this.description = description;
    }

}
