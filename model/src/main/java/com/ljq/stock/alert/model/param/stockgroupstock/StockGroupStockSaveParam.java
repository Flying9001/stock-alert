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
@ApiModel(value = "用户股票分组关联股票保存(单条)", description = "用户股票分组关联股票保存(单条)")
public class StockGroupStockSaveParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分组 id
     * */
    @NotNull(message = "分组 id 不能为空")
    @Min(value = 1, message = "分组 id 至少为 1")
    @ApiModelProperty(value = "分组 id 不能为空,至少为 1", name = "stockGroupId", required = true, example = "0")
    private Long stockGroupId;
    /**
     * 股票 id
     * */
    @NotNull(message = "股票 id 不能为空")
    @Min(value = 1, message = "股票 id 至少为 1")
    @ApiModelProperty(value = "股票 id 不能为空,至少为 1", name = "stockId", required = true, example = "0")
    private Long stockId;


}
