package com.ljq.stock.alert.model.param.stocksource;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * @Description: 股票公共请求参数
 * @Author: junqiang.lu
 * @Date: 2021/3/25
 */
@Data
public class StockSourceCommonParam implements Serializable {

    private static final long serialVersionUID = 2983853539107843997L;

    /**
     * 市场类型,1-上海,2-深圳,3-香港,4-美国
     * */
    @NotNull(message = "市场类型不能为空")
    @Min(value = 1, message = "市场类型设置错误")
    @Max(value = 2, message = "市场类型设置错误")
    @ApiModelProperty(value = "市场类型,1-上海,2-深圳", name = "marketType", required = true, example = "0")
    private Integer marketType;
    /**
     * 股票代码
     * */
    @NotBlank(message = "股票代码 不能为空")
    @Pattern(regexp = "^[0-9a-zA-Z]{1,10}$", message = "股票代码不合法，请重新输入")
    @ApiModelProperty(value = "股票代码", name = "stockCode", required = true)
    private String stockCode;

}
