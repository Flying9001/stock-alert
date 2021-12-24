package com.ljq.stock.alert.model.param.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;

/**
 * 预警消息分页查询
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Data
@ToString(callSuper = true)
@ApiModel(value = "预警消息分页查询", description = "预警消息分页查询")
public class AlertMessageListParam extends AlertMessageListUserParam {

    private static final long serialVersionUID = 1L;

    /**
     * 用户信息
     * */
    @Min(value = 1, message = "用户信息ID至少为1")
    @ApiModelProperty(value = "用户信息 不能为空,至少为 1", name = "userId", example = "0")
    private Long userId;


}
