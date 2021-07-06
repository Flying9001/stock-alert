package com.ljq.stock.alert.model.param.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Description: 查询用户的消息详情
 * @Author: junqiang.lu
 * @Date: 2021/4/15
 */
@Data
@ApiModel(value = "查询用户的消息详情", description = "查询用户的消息详情")
public class AlertMessageInfoUserParam extends AlertMessageInfoParam {

    private static final long serialVersionUID = -7420816887461218767L;

    /**
     * 用户信息
     * */
    @NotNull(message = "请选择接收通知的用户")
    @Min(value = 1, message = "用户 ID 至少为 1")
    @ApiModelProperty(value = "用户信息 不能为空,至少为 1", name = "userId", required = true, example = "0")
    private Long userId;

}
