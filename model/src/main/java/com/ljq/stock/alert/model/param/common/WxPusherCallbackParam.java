package com.ljq.stock.alert.model.param.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Description: WxPusher 回调请求参数
 * @Author: junqiang.lu
 * @Date: 2023/8/21
 */
@Data
@ApiModel(value = "WxPusher 回调请求参数", description = "WxPusher 回调请求参数")
public class WxPusherCallbackParam implements Serializable {

    private static final long serialVersionUID = 5397163159342108378L;

    @NotBlank(message = "动作不能为空")
    @ApiModelProperty(value = "动作", required = true)
    private String action;

    @ApiModelProperty(value = "请求参数")
    private ReqData data;


    /**
     * 请求参数
     */
    @Data
    public static class ReqData implements Serializable{

        private static final long serialVersionUID = -4386696145244580474L;

        @ApiModelProperty(value = "应用id")
        private String appId;

        @ApiModelProperty(value = "应用名称")
        private String appName;

        @ApiModelProperty(value = "用户关注渠道，scan表示扫码关注，link表示链接关注，command表示通过消息关注应用，" +
                "后期可能还会添加其他渠道。")
        private String source;

        @ApiModelProperty(value = "用户名，新用户微信不再返回 ，强制返回空")
        private String userName;

        @ApiModelProperty(value = "用户头像地址，新用户微信不再返回 ，强制返回空")
        private String userHeadImg;

        @ApiModelProperty(value = "消息发生时间")
        private String time;

        @NotBlank(message = "用户推送ID不能为空")
        @ApiModelProperty(value = "用户uid", required = true)
        private String uid;

        @NotBlank(message = "用户绑定参数不能为空")
        @ApiModelProperty(value = "用户扫描带参数的二维码，二维码携带的参数。用户关注回调时为用户id", required = true)
        private String extra;

    }

}
