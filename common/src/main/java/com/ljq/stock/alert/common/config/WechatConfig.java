package com.ljq.stock.alert.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 微信配置信息
 * @Author: junqiang.lu
 * @Date: 2022/8/2
 */
@Getter
@Configuration
public class WechatConfig {

    /**
     * 微信小程序 AppId
     */
    @Value(value = "${wechat.mini.appId : null}")
    private String miniAppId;

    /**
     * 微信小程序秘钥
     */
    @Value(value = "${wechat.mini.appSecret : null}")
    private String miniAppSecret;

    /**
     * 微信小程序登录地址
     */
    @Value(value = "${wechat.mini.loginUrl : null}")
    private String miniLoginUrl;

    /**
     * 小程序消息推送服务器认证凭证
     */
    @Value(value = "${wechat.mini.msgToken : null}")
    private String miniMsgToken;

    /**
     * 小程序消息AES加密秘钥
     */
    @Value(value = "${wechat.mini.msgAesKey : null}")
    private String miniMsgAesKey;





}
