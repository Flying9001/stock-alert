package com.ljq.stock.alert.model.param.userpushtype;

import com.ljq.stock.alert.model.BaseInfoParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 用户消息推送方式参数接收类
 *
 * @author junqiang.lu
 * @date 2023-07-31 18:07:31
 */
@Data
@ApiModel(value = "用户消息推送方式删除(单条)", description = "用户消息推送方式删除(单条)")
public class UserPushTypeDeleteParam extends BaseInfoParam {

    private static final long serialVersionUID = 1L;

    /**
     * id
     * */
    @NotNull(message = "id 不能为空")
    @Min(value = 1, message = "id 至少为 1")
    @ApiModelProperty(value = "id 不能为空,至少为 1", name = "id", required = true, example = "0")
    private Long id;
    /**
     * 用户id
     * */
    @NotNull(message = "用户id 不能为空")
    @Min(value = 1, message = "用户id 至少为 1")
    @ApiModelProperty(value = "用户id 不能为空,至少为 1", name = "userId", required = true, example = "0")
    private Long userId;
    /**
     * 推送方式,1-短信;2-邮件;3-pushplus
     * */
    @NotNull(message = "推送方式,1-短信;2-邮件;3-pushplus 不能为空")
    @Min(value = 1, message = "推送方式,1-短信;2-邮件;3-pushplus 至少为 1")
    @ApiModelProperty(value = "推送方式,1-短信;2-邮件;3-pushplus 不能为空,至少为 1", name = "pushType", required = true, example = "0")
    private Integer pushType;
    /**
     * 通知推送接收地址
     * */
    @NotNull(message = "通知推送接收地址 不能为空")
    @ApiModelProperty(value = "通知推送接收地址", name = "receiveAddress", required = true)
    private String receiveAddress;
    /**
     * 是否启用,0-未启用,1-启用
     * */
    @NotNull(message = "是否启用,0-未启用,1-启用 不能为空")
    @Min(value = 1, message = "是否启用,0-未启用,1-启用 至少为 1")
    @ApiModelProperty(value = "是否启用,0-未启用,1-启用 不能为空,至少为 1", name = "enable", required = true, example = "0")
    private Integer enable;
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
