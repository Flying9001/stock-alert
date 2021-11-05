package com.ljq.stock.alert.model.param.userstock;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 用户股票修改(单条)
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Data
@ToString(callSuper = true)
@ApiModel(value = "用户股票修改(单条)", description = "用户股票修改(单条)")
public class UserStockUpdateParam extends UserStockInfoParam {

    private static final long serialVersionUID = 1L;

    /**
     * 股价预警最高价
     * */
    @ApiModelProperty(value = "股价预警最高价", name = "maxPrice", required = true)
    private BigDecimal maxPrice;
    /**
     * 股价预警最低价
     * */
    @ApiModelProperty(value = "股价预警最低价", name = "minPrice", required = true)
    private BigDecimal minPrice;


}
