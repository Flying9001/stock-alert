package com.ljq.stock.alert.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description: 股票指数展示对象
 * @Author: junqiang.lu
 * @Date: 2022/2/9
 */
@Data
@ApiModel(value = "股票指数展示对象", description = "股票指数展示对象")
public class StockIndexVo implements Serializable {

    private static final long serialVersionUID = 5069341244526955732L;

    /**
     * 指数名称
     */
    @ApiModelProperty(value = "指数名称", name = "indexName")
    private String indexName;
    /**
     * 当前值
     */
    @ApiModelProperty(value = "当前值", name = "currentValue")
    private BigDecimal currentValue;
    /**
     * 涨跌额
     **/
    @ApiModelProperty(value = "涨跌额", name = "increase")
    private BigDecimal increase;
    /**
     * 涨跌百分比
     **/
    @ApiModelProperty(value = "涨跌百分比", name = "increasePer")
    private BigDecimal increasePer;



}
