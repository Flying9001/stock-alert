package com.ljq.stock.alert.common.component;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import com.ljq.stock.alert.common.config.PushPlusConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @Description: PushPlus 消息推送客户端
 * @Author: junqiang.lu
 * @Date: 2023/7/26
 */
@Slf4j
@Component
public class PushPlusClient {

    @Resource
    private PushPlusConfig pushPlusConfig;


    /**
     * 推送消息
     *
     * @param pushParam
     */
    public void push(PushPlusPushParam pushParam) {
        String response = HttpUtil.post(pushPlusConfig.getApiPushWechatPublic(), BeanUtil.beanToMap(pushParam));
        log.info("pushPlus push response: {}", response);
    }

    @Data
    @ApiModel(value = "PushPlus 推送请求参数", description = "PushPlus 推送请求参数")
    public static class PushPlusPushParam implements Serializable {

        private static final long serialVersionUID = -9050774274884470793L;

        @ApiModelProperty(value = "token", required = true)
        private String token;

        @ApiModelProperty(value = "消息标题")
        private String title;

        @ApiModelProperty(value = "消息具体内容", required = true)
        private String content;

        @ApiModelProperty(value = "消息模板")
        private String template;

        @ApiModelProperty(value = "发送渠道")
        private String channel;

        @ApiModelProperty(value = "webhook编码")
        private String webhook;

        @ApiModelProperty(value = "异步回调地址")
        private String callbackUrl;

        @ApiModelProperty(value = "时间戳，毫秒。如小于当前时间，消息将无法发送")
        private String timestamp;
    }



}
