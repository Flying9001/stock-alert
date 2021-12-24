package com.ljq.stock.alert.model.param.userstock;

import com.ljq.stock.alert.model.param.stocksource.StockSourceListParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

/**
 * 用户股票分页查询
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Data
@ToString(callSuper = true)
@ApiModel(value = "用户股票分页查询", description = "用户股票分页查询")
public class UserStockListParam extends StockSourceListParam {

    private static final long serialVersionUID = 1L;

}
