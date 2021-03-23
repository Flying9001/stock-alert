package com.ljq.stock.alert.model.param.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

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
    @NotNull(message = "用户信息 不能为空")
    @Min(value = 1, message = "用户信息 至少为 1")
    @ApiModelProperty(value = "用户信息 不能为空,至少为 1", name = "userId", required = true, example = "0")
    private Long userId;
    /**
     * 手机发送,1-发送成功,2-发送失败,3-未发送
     * */
    @NotNull(message = "手机发送,1-发送成功,2-发送失败,3-未发送 不能为空")
    @Min(value = 1, message = "手机发送,1-发送成功,2-发送失败,3-未发送 至少为 1")
    @ApiModelProperty(value = "手机发送,1-发送成功,2-发送失败,3-未发送 不能为空,至少为 1", name = "phoneSend", required = true, example = "0")
    private Integer phoneSend;
    /**
     * 邮箱发送,1-发送成功,2-发送失败,3-未发送
     * */
    @NotNull(message = "邮箱发送,1-发送成功,2-发送失败,3-未发送 不能为空")
    @Min(value = 1, message = "邮箱发送,1-发送成功,2-发送失败,3-未发送 至少为 1")
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
     * 消息内容
     * */
    @NotNull(message = "消息内容 不能为空")
    @ApiModelProperty(value = "消息内容", name = "content", required = true)
    private String content;
    /**
     * 创建时间
     * */
    @NotNull(message = "创建时间 不能为空")
    @ApiModelProperty(value = "创建时间", name = "createTime", required = true)
    private Date createTime;
    /**
     * 更新时间
     * */
    @NotNull(message = "更新时间 不能为空")
    @ApiModelProperty(value = "更新时间", name = "updateTime", required = true)
    private Date updateTime;


}
