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
@ApiModel(value = "用户股票分组关联股票修改(单条)", description = "用户股票分组关联股票修改(单条)")
public class StockGroupStockUpdateParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id,主键
     * */
    @NotNull(message = "id,主键 不能为空")
    @Min(value = 1, message = "id,主键 至少为 1")
    @ApiModelProperty(value = "id,主键 不能为空,至少为 1", name = "id", required = true, example = "0")
    private Long id;
    /**
     * 分组 id
     * */
    @NotNull(message = "分组 id 不能为空")
    @Min(value = 1, message = "分组 id 至少为 1")
    @ApiModelProperty(value = "分组 id 不能为空,至少为 1", name = "stockGroupId", required = true, example = "0")
    private Long stockGroupId;
    /**
     * 用户关注股票 id
     * */
    @NotNull(message = "用户关注股票 id 不能为空")
    @Min(value = 1, message = "用户关注股票 id 至少为 1")
    @ApiModelProperty(value = "用户关注股票 id 不能为空,至少为 1", name = "userStockId", required = true, example = "0")
    private Long userStockId;
    /**
     * 创建时间
     * */
    @NotNull(message = "创建时间 不能为空")
    @ApiModelProperty(value = "创建时间", name = "createTime", required = true)
    private String createTime;
    /**
     * 更新时间
     * */
    @NotNull(message = "更新时间 不能为空")
    @ApiModelProperty(value = "更新时间", name = "updateTime", required = true)
    private String updateTime;


}
