package com.ljq.stock.alert.model.param.adminuser;

import com.ljq.stock.alert.model.BaseInfoParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Description: 管理员用户启用与禁用请求参数
 * @Author: junqiang.lu
 * @Date: 2022/8/15
 */
@Data
@ToString(callSuper = true)
@ApiModel(value = "管理员用户启用与禁用请求参数", description = "管理员用户启用与禁用请求参数")
public class AdminUserEnableParam extends BaseInfoParam {

    private static final long serialVersionUID = 3363765213667301178L;

    /**
     * 是否启用,0-禁用,1-启用
     */
    @NotNull(message = "账号启用状态不能为空")
    @Min(value = 0, message = "账号启用状态设置错误")
    @Max(value = 1, message = "账号启用状态设置错误")
    @ApiModelProperty(value = "是否启用,0-禁用,1-启用", name = "enable", example = "0", required = true)
    private Integer enable;

}
