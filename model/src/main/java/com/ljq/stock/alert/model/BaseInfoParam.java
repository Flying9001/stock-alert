package com.ljq.stock.alert.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description: 查询详情基础参数类
 * @Author: junqiang.lu
 * @Date: 2021/4/12
 */
@Data
@ApiModel(value = "查询详情", description = "查询详情")
public class BaseInfoParam implements Serializable {

    private static final long serialVersionUID = -4477890894587488060L;

    /**
     * id
     **/
    @NotNull(message = "请选择需要查询的对象")
    @Min(value = 1, message = "请求参数不合理")
    @ApiModelProperty(value = "id", name = "id", required = true)
    private Long id;

}
