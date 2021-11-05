package com.ljq.stock.alert.model.param.stocksource;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

/**
 * @Description: 按照股票代码删除股票
 * @Author: junqiang.lu
 * @Date: 2021/3/25
 */
@Data
@ToString(callSuper = true)
@ApiModel(value = "按照股票代码删除股票", description = "按照股票代码删除股票")
public class StockSourceDeleteByCodeParam extends StockSourceCommonParam {

    private static final long serialVersionUID = -5157727616513766866L;

}
