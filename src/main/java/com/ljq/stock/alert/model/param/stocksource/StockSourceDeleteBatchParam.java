package com.ljq.stock.alert.model.param.stocksource;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 股票源批量删除
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Data
@ApiModel(value = "股票源批量删除", description = "股票源删除批量")
public class StockSourceDeleteBatchParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id 列表
     **/
    @NotEmpty(message = "id 不能为空")
    @ApiModelProperty(value = "id不能为空", name = "idList")
    private List<Long> idList;


}
