package com.ljq.stock.alert.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ljq.stock.alert.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户股票实体类
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Data
@TableName(value = "user_stock", resultMap = "userStockMap")
@ApiModel(value = "用户股票", description = "用户股票")
public class UserStockEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

    /**
     * 股票 id
     **/
    @TableField(value = "STOCK_ID")
    @ApiModelProperty(value = "股票 id", name = "stockId")
    private Long stockId;
    /**
     * 用户 id
     **/
    @TableField(value = "USER_ID")
    @ApiModelProperty(value = "用户 id", name = "userId")
    private Long userId;
    /**
     * 股价预警最高价
     **/
    @TableField(value = "MAX_PRICE")
    @ApiModelProperty(value = "股价预警最高价", name = "maxPrice")
    private BigDecimal maxPrice;
    /**
     * 股价预警最低价
     **/
    @TableField(value = "MIN_PRICE")
    @ApiModelProperty(value = "股价预警最低价", name = "minPrice")
    private BigDecimal minPrice;
    /**
     * 股票信息
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "股票信息", name = "stockSource")
    private StockSourceEntity stockSource;

}
