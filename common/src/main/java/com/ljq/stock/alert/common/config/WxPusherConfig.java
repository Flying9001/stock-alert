package com.ljq.stock.alert.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: wxPusher 参数配置
 * @Author: junqiang.lu
 * @Date: 2023/8/21
 */
@Getter
@Configuration
public class WxPusherConfig {

    /**
     * app Token
     */
    @Value(value = "${wxPusher.appToken: null}")
    private String appToken;

    /**
     * 成功返回状态码
     */
    @Value(value = "${wxPusher.successCode: 1000}")
    private Integer successCode;

    /**
     * 用户关注动作
     */
    @Value(value = "${wxPusher.action.subscribe: app_subscribe}")
    private String actionSubscribe;

    /**
     * 二维码有效期，单位: 秒
     */
    @Value(value = "${wxPusher.qrCode.validTime: 1800}")
    private Long qrCodeValidTime;

    /**
     * 创建二维码接口路径
     */
    @Value(value = "${wxPusher.api.createQrCode: null}")
    private String apiCreateQrCode;


}
