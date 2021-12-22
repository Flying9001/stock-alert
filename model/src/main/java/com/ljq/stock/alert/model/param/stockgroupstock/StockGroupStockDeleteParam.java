package com.ljq.stock.alert.model.param.stockgroupstock;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
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
     * id,主键
     * */
    @NotNull(message = "ID不能为空")
    @Min(value = 1, message = "ID至少为 1")
    @ApiModelProperty(value = "id,主键 不能为空,至少为 1", name = "id", required = true, example = "0")
    private Long id;

}
