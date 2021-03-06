package com.ljq.stock.alert.model.param.userstock;

import com.ljq.stock.alert.model.param.stocksource.StockSourceCommonParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 用户股票新增(单条)
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Data
@ApiModel(value = "用户股票新增(单条)", description = "用户股票新增(单条)")
public class UserStockSaveParam extends StockSourceCommonParam {

    private static final long serialVersionUID = 1L;

    /**
     * 用户 id
     * */
    @NotNull(message = "用户 id 不能为空")
    @Min(value = 1, message = "用户 id 至少为 1")
    @ApiModelProperty(value = "用户 id 不能为空,至少为 1", name = "userId", required = true, example = "0")
    private Long userId;
    /**
     * 股价预警最高价
     * */
    @NotNull(message = "股价预警最高价 不能为空")
    @Min(value = 0, message = "股价预警最高价不能未负")
    @ApiModelProperty(value = "股价预警最高价", name = "maxPrice", required = true)
    private BigDecimal maxPrice;
    /**
     * 股价预警最低价
     * */
    @NotNull(message = "股价预警最低价 不能为空")
    @Min(value = 0, message = "股价预警最低价不能未负")
    @ApiModelProperty(value = "股价预警最低价", name = "minPrice", required = true)
    private BigDecimal minPrice;


}
