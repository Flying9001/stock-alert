package com.ljq.stock.alert.model.param.stocksource;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
    @NotNull(message = "id 不能为空")
    @ApiModelProperty(value = "id不能为空", name = "id")
    private Long id;


}
