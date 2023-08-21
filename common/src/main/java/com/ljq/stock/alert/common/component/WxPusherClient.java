package com.ljq.stock.alert.common.component;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ljq.stock.alert.common.config.WxPusherConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @Description: WxPusher 消息推送客户端
 * @Author: junqiang.lu
 * @Date: 2023/8/21
 */
@Slf4j
@Component
public class WxPusherClient {

    @Resource
    private WxPusherConfig wxPusherConfig;


    /**
     * 创建二维码
     *
     * @param createQrCodeParam
     * @return
     */
    public String createQrCode(CreateQrCodeParam createQrCodeParam) {
        createQrCodeParam.setAppToken(wxPusherConfig.getAppToken());
        createQrCodeParam.setValidTime(wxPusherConfig.getQrCodeValidTime());
        String responseStr = HttpUtil.post(wxPusherConfig.getApiCreateQrCode(), JSONUtil.toJsonStr(createQrCodeParam),
                60000);
        JSONObject responseJson = JSONUtil.parseObj(responseStr);
        Integer responseCode = responseJson.get("code", Integer.class, true);
        if (!Objects.equals(responseCode, wxPusherConfig.getSuccessCode())) {
            log.warn("wxPush qrcode create error,reason: {}",responseStr);
            return "";
        }
        return responseJson.getByPath("data.url", String.class);
    }

    /**
     * 推送消息
     *
     * @param pushParam
     * @return
     */
    public String push(PushParam pushParam) {
        pushParam.setAppToken(wxPusherConfig.getAppToken());
        String responseStr = HttpUtil.post(wxPusherConfig.getApiPush(), JSONUtil.toJsonStr(pushParam));
        JSONObject responseJson = JSONUtil.parseObj(responseStr);
        Integer responseCode = responseJson.get("code", Integer.class, true);
        if (!Objects.equals(responseCode, wxPusherConfig.getSuccessCode())) {
            log.warn("wxPush qrcode create error,reason: {}",responseStr);
            return "";
        }
        JSONArray jsonArray = responseJson.getJSONArray("data");
        return jsonArray.get(0,JSONObject.class, true).getStr("sendRecordId");
    }


    @Data
    @ApiModel(value = "WxPusher 创建二维码请求参数")
    public static class CreateQrCodeParam implements Serializable {

        private static final long serialVersionUID = -1392177204205524737L;

        @ApiModelProperty(value = "应用 token")
        private String appToken;

        @ApiModelProperty(value = "二维码携带的参数，最长64位")
        private String extra;

        @ApiModelProperty(value = "二维码的有效期，默认30分钟，最长30天，单位是秒")
        private Long validTime;

    }

    @Data
    @ApiModel(value = "WxPusher 消息推送客户端")
    public static class PushParam implements Serializable {

        private static final long serialVersionUID = -7772631746021858570L;

        @ApiModelProperty(value = "应用 token", required = true)
        private String appToken;

        @ApiModelProperty(value = "消息摘要", required = true)
        private String summary;

        @ApiModelProperty(value = "消息内容", required = true)
        private String content;

        @ApiModelProperty(value = "内容类型 1表示文字  2表示html(只发送body标签内部的数据即可，不包括body标签) 3表示markdown ",
                required = true)
        private Integer contentType;

        @ApiModelProperty(value = "发送目标的topicId，是一个数组！！！，也就是群发，使用uids单发的时候， 可以不传")
        private List<String> topicIds;

        @ApiModelProperty(value = "发送目标的UID，是一个数组。注意uids和topicIds可以同时填写，也可以只填写一个。", required = true)
        private List<String> uids;

        @ApiModelProperty(value = "原文链接")
        private String url;

        @ApiModelProperty(value = "是否验证订阅时间，true表示只推送给付费订阅用户，false表示推送的时候，不验证付费，" +
                "不验证用户订阅到期时间，用户订阅过期了，也能收到。")
        private String verifyPay;


    }

}
