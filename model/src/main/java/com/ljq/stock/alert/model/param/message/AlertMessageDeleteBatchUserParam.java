package com.ljq.stock.alert.model.param.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Description: 批量删除用户消息
 * @Author: junqiang.lu
 * @Date: 2021/4/15
 */
@Data
@ToString(callSuper = true)
@ApiModel(value = "批量删除用户消息", description = "批量删除用户消息")
public class AlertMessageDeleteBatchUserParam extends AlertMessageDeleteBatchParam{

    private static final long serialVersionUID = -2876946836959375841L;

    /**
     * 用户信息
     * */
    @NotNull(message = "请选择接收通知的用户")
    @Min(value = 1, message = "用户 ID 至少为 1")
    @ApiModelProperty(value = "用户信息 不能为空,至少为 1", name = "userId", required = true, example = "0")
    private Long userId;
}
