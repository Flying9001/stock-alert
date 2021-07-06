package com.ljq.stock.alert.model.param.stocksource;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 股票源新增(单条)
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Data
@ApiModel(value = "股票源新增(单条)", description = "股票源新增(单条)")
public class StockSourceSaveParam extends StockSourceCommonParam {

    private static final long serialVersionUID = 1L;
}
