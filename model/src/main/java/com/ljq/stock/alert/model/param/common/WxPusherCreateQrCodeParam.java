package com.ljq.stock.alert.model.param.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: wxPusher 创建二维码请求参数
 * @Author: junqiang.lu
 * @Date: 2023/8/21
 */
@Data
@ApiModel(value = "wxPusher 创建二维码请求参数")
public class WxPusherCreateQrCodeParam implements Serializable {

    private static final long serialVersionUID = -1392177204205524737L;

    @ApiModelProperty(value = "应用 token")
    private String appToken;

    @ApiModelProperty(value = "二维码携带的参数，最长64位")
    private String extra;

    @ApiModelProperty(value = "二维码的有效期，默认30分钟，最长30天，单位是秒")
    private Long validTime;


}
