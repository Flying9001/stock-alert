package com.ljq.stock.alert.common.config;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: pushplus 配置信息
 * @Author: junqiang.lu
 * @Date: 2023/7/26
 */
@Getter
@ToString
@Configuration
public class PushPlusConfig {

    /**
     * 微信公众号推送消息接口地址
     */
    @Value("${pushPlus.api.pushWechatPublic}")
    private String apiPushWechatPublic;




}
