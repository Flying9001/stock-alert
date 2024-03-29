package com.ljq.stock.alert.model.param.message;

import com.ljq.stock.alert.model.BaseInfoParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;

/**
 * 预警消息查询详情(单条)
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Data
@ToString(callSuper = true)
@ApiModel(value = "预警消息查询详情(单条)", description = "预警消息查询详情(单条)")
public class AlertMessageInfoParam extends BaseInfoParam {

    private static final long serialVersionUID = 1L;

    /**
     * 用户信息
     * */
    @Min(value = 1, message = "用户 ID 至少为 1")
    @ApiModelProperty(value = "用户信息 不能为空,至少为 1", name = "userId", example = "0")
    private Long userId;

}
