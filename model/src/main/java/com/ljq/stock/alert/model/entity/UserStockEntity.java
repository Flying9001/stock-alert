package com.ljq.stock.alert.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ljq.stock.alert.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 用户股票实体类
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Data
@ToString(callSuper = true)
@TableName(value = "USER_STOCK", resultMap = "userStockMap")
@ApiModel(value = "用户股票", description = "用户股票")
public class UserStockEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

    /**
     * 股票 id
     **/
    @JsonSerialize(using= ToStringSerializer.class)
    @TableField(value = "STOCK_ID")
    @ApiModelProperty(value = "股票 id", name = "stockId")
    private Long stockId;
    /**
     * 用户 id
     **/
    @JsonSerialize(using=ToStringSerializer.class)
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
     * 单日股价最大涨幅(值为正,最少为1,即1%),默认-1(A股标准涨停)
     */
    @TableField(value = "MAX_INCREASE_PER")
    @ApiModelProperty(value = "单日股价最大涨幅(%)", name = "maxIncreasePer")
    private Integer maxIncreasePer;
    /**
     * 单日股价最大跌幅(值为正,最少为1,即1%),默认-1(A股标准跌停)
     */
    @TableField(value = "MAX_DECREASE_PER")
    @ApiModelProperty(value = "单日股价最大跌幅(%)", name = "maxDecreasePer")
    private Integer maxDecreasePer;
    /**
     * 股票信息
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "股票信息", name = "stockSource")
    private StockSourceEntity stockSource;
    /**
     * 用户信息
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "用户信息", name = "userInfo")
    private UserInfoEntity userInfo;


}
