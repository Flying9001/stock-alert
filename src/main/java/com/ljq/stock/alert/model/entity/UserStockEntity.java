package com.ljq.stock.alert.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ljq.stock.alert.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

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
     * id
     **/
    @TableId(value = "ID", type = IdType.AUTO)
    @ApiModelProperty(value = "id", name = "id")
    private Long id;
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
     * 创建时间
     **/
    @TableField(value = "CREATE_TIME")
    @ApiModelProperty(value = "创建时间", name = "createTime")
    private Date createTime;
    /**
     * 更新时间
     **/
    @TableField(value = "UPDATE_TIME")
    @ApiModelProperty(value = "更新时间", name = "updateTime")
    private Date updateTime;

}
