package com.ljq.stock.alert.model.param.userstock;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户股票修改(单条)
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Data
@ApiModel(value = "用户股票修改(单条)", description = "用户股票修改(单条)")
public class UserStockUpdateParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     **/
    @NotNull(message = "id 不能为空")
    @ApiModelProperty(value = "id 不能为空", name = "id")
    private Long id;
    /**
     * 股票 id
     * */
    @NotNull(message = "股票 id 不能为空")
    @Min(value = 1, message = "股票 id 至少为 1")
    @ApiModelProperty(value = "股票 id 不能为空,至少为 1", name = "stockId", required = true, example = "0")
    private Long stockId;
    /**
     * 用户 id
     * */
    @NotNull(message = "用户 id 不能为空")
    @Min(value = 1, message = "用户 id 至少为 1")
    @ApiModelProperty(value = "用户 id 不能为空,至少为 1", name = "userId", required = true, example = "0")
    private Long userId;
    /**
     * 股价预警最高价
     * */
    @NotNull(message = "股价预警最高价 不能为空")
    @ApiModelProperty(value = "股价预警最高价", name = "maxPrice", required = true)
    private BigDecimal maxPrice;
    /**
     * 股价预警最低价
     * */
    @NotNull(message = "股价预警最低价 不能为空")
    @ApiModelProperty(value = "股价预警最低价", name = "minPrice", required = true)
    private BigDecimal minPrice;
    /**
     * 创建时间
     * */
    @NotNull(message = "创建时间 不能为空")
    @ApiModelProperty(value = "创建时间", name = "createTime", required = true)
    private Date createTime;
    /**
     * 更新时间
     * */
    @NotNull(message = "更新时间 不能为空")
    @ApiModelProperty(value = "更新时间", name = "updateTime", required = true)
    private Date updateTime;


}
