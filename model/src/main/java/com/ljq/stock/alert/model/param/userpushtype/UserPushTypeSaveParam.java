package com.ljq.stock.alert.model.param.userpushtype;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户消息推送方式参数接收类
 *
 * @author junqiang.lu
 * @date 2023-07-31 18:07:31
 */
@Data
@ApiModel(value = "用户消息推送方式保存(单条)", description = "用户消息推送方式保存(单条)")
public class UserPushTypeSaveParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 推送方式,1-短信;2-邮件;3-pushplus
     * */
    @NotNull(message = "推送方式不能为空")
    @Min(value = 31, message = "推送方式错误")
    @Max(value = 31, message = "推送方式错误")
    @ApiModelProperty(value = "推送方式,31-pushplus 微信公众号", name = "pushType", required = true, example = "0")
    private Integer pushType;
    /**
     * 通知推送接收地址
     * */
    @NotBlank(message = "通知推送接收地址 不能为空")
    @ApiModelProperty(value = "通知推送接收地址", name = "receiveAddress", required = true)
    private String receiveAddress;
    /**
     * 是否启用,0-未启用,1-启用
     * */
    @NotNull(message = "是否启用不能为空")
    @Min(value = 0, message = "是否启用设置错误")
    @Max(value = 1, message = "是否启用设置错误")
    @ApiModelProperty(value = "是否启用,0-未启用,1-启用", name = "enable", required = true, example = "0")
    private Integer enable;


}
