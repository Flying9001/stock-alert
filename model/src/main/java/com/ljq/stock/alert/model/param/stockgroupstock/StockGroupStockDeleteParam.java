package com.ljq.stock.alert.model.param.stockgroupstock;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户股票分组关联股票参数接收类
 *
 * @author junqiang.lu
 * @date 2021-12-21 11:21:28
 */
@Data
@ApiModel(value = "用户股票分组关联股票删除(单条)", description = "用户股票分组关联股票删除(单条)")
public class StockGroupStockDeleteParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     **/
    @NotNull(message = "请选择需要删除的对象")
    @ApiModelProperty(value = "id", name = "id", required = true)
    private Long id;

}
