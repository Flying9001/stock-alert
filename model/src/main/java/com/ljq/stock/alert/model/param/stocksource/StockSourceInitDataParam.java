package com.ljq.stock.alert.model.param.stocksource;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * @Description: 初始化股票数据
 * @Author: junqiang.lu
 * @Date: 2023/11/29
 */
@Data
@ApiModel(value = "初始化股票数据")
public class StockSourceInitDataParam implements Serializable {

    private static final long serialVersionUID = -516632863170108114L;

    @Length(max = 10, message = "请输入正确的数据源")
    @ApiModelProperty(value = "数据源,sina,mydata 等，默认sina")
    private String dataSource;

}
