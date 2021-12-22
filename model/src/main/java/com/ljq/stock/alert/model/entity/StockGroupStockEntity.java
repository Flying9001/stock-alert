package com.ljq.stock.alert.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ljq.stock.alert.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 用户股票分组关联股票实体类
 *
 * @author junqiang.lu
 * @date 2021-12-21 11:21:28
 */
@Data
@ToString(callSuper = true)
@TableName(value = "STOCK_GROUP_STOCK")
@ApiModel(value = "用户股票分组关联股票", description = "用户股票分组关联股票")
public class StockGroupStockEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

    /**
	 * 分组 id
	 * */
    @TableField(value = "STOCK_GROUP_ID")
	@ApiModelProperty(value = "分组 id", name = "stockGroupId")
	private Long stockGroupId;
    /**
	 * 股票 id
	 * */
    @TableField(value = "STOCK_ID")
	@ApiModelProperty(value = "股票 id", name = "stockId")
	private Long stockId;
	/**
	 * 用户股票分组名称
	 */
	@TableField(exist = false)
	@ApiModelProperty(value = "用户股票分组", name = "userStockGroupName")
	private String userStockGroupName;
	/**
	 * 股票源
	 */
	@TableField(exist = false)
	@ApiModelProperty(value = "股票源", name = "stockSource")
    private StockSourceEntity stockSource;

}
