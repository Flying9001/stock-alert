package com.ljq.stock.alert.model.param.stocksource;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Description: 分页查询实时股票数据
 * @Author: junqiang.lu
 * @Date: 2021/3/25
 */
@Data
@ApiModel(value = "分页查询实时股票数据", description = "分页查询实时股票数据")
public class StockSourceListRealTimeParam extends StockSourceListParam {

    private static final long serialVersionUID = 6210744092609309770L;

}
