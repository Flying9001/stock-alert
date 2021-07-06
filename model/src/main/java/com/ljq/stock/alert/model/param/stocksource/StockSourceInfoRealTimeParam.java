package com.ljq.stock.alert.model.param.stocksource;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Description: 查询某一支股票实时数据
 * @Author: junqiang.lu
 * @Date: 2021/3/26
 */
@Data
@ApiModel(value = "查询某一支股票实时数据",description = "查询某一支实时数据")
public class StockSourceInfoRealTimeParam extends StockSourceInfoParam {

    private static final long serialVersionUID = 6360938434911413430L;

}
