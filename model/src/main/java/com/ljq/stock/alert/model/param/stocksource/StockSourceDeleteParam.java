package com.ljq.stock.alert.model.param.stocksource;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 股票源删除(单条)
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Data
@ApiModel(value = "股票源删除(单条)", description = "股票源删除(单条)")
public class StockSourceDeleteParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     **/
    @NotNull(message = "请选择需要删除的对象")
    @Min(value = 1, message = "请求参数不合理")
    @ApiModelProperty(value = "id", name = "id", required = true)
    private Long id;


}
