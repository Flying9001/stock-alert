package com.ljq.stock.alert.model.param.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 预警消息新增(单条)
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Data
@ApiModel(value = "预警消息新增(单条)", description = "预警消息新增(单条)")
public class AlertMessageSaveParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户信息
     * */
    @NotNull(message = "请选择接收通知的用户")
    @Min(value = 1, message = "用户 ID 至少为 1")
    @ApiModelProperty(value = "用户信息 不能为空,至少为 1", name = "userId", required = true, example = "0")
    private Long userId;
    /**
     * 手机发送,1-发送成功,2-发送失败,3-未发送
     * */
    @NotNull(message = "请选择手机提醒发送状态")
    @Min(value = 1, message = "手机提醒发送状态设置错误")
    @Max(value = 3, message = "手机提醒发送状态设置错误")
    @ApiModelProperty(value = "手机发送,1-发送成功,2-发送失败,3-未发送 不能为空,至少为 1", name = "phoneSend", required = true, example = "0")
    private Integer phoneSend;
    /**
     * 邮箱发送,1-发送成功,2-发送失败,3-未发送
     * */
    @NotNull(message = "请选择邮箱提醒发送状态")
    @Min(value = 1, message = "邮箱提醒发送状态设置错误")
    @Max(value = 3, message = "邮箱提醒发送状态设置错误")
    @ApiModelProperty(value = "邮箱发送,1-发送成功,2-发送失败,3-未发送 不能为空,至少为 1", name = "emailSend", required = true, example = "0")
    private Integer emailSend;
    /**
     * 股票 id
     * */
    @NotNull(message = "股票 id 不能为空")
    @Min(value = 1, message = "股票 id 至少为 1")
    @ApiModelProperty(value = "股票 id 不能为空,至少为 1", name = "stockId", required = true, example = "0")
    private Long stockId;
    /**
     * 消息标题
     */
    @NotBlank(message = "消息标题不能为空")
    @Length(max = 64, message = "消息标题需要控制在 64 支付以内")
    @ApiModelProperty(value = "消息标题", name = "title", required = true)
    private String title;
    /**
     * 消息内容
     * */
    @NotBlank(message = "消息内容 不能为空")
    @Length(max = 256, message = "消息内容不能为空")
    @ApiModelProperty(value = "消息内容", name = "content", required = true)
    private String content;


}
