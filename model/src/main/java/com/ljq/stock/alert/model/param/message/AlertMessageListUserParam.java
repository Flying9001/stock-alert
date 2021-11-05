package com.ljq.stock.alert.model.param.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Description: 查询用户消息列表
 * @Author: junqiang.lu
 * @Date: 2021/4/15
 */
@Data
@ToString(callSuper = true)
@ApiModel(value = "查询用户消息列表", description = "查询用户消息列表")
public class AlertMessageListUserParam extends AlertMessageListParam {

    private static final long serialVersionUID = -2961655977467552180L;

    /**
     * 用户信息
     * */
    @NotNull(message = "请选择接收通知的用户")
    @Min(value = 1, message = "用户 ID 至少为 1")
    @ApiModelProperty(value = "用户信息 不能为空,至少为 1", name = "userId", required = true, example = "0")
    private Long userId;

}
