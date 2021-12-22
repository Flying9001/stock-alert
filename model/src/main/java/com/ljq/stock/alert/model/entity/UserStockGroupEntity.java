package com.ljq.stock.alert.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ljq.stock.alert.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 用户股票分组实体类
 *
 * @author junqiang.lu
 * @date 2021-12-21 11:21:27
 */
@Data
@ToString(callSuper = true)
@TableName(value = "USER_STOCK_GROUP")
@ApiModel(value = "用户股票分组", description = "用户股票分组")
public class UserStockGroupEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

    /**
	 * 分组名称
	 * */
    @TableField(value = "GROUP_NAME")
	@ApiModelProperty(value = "分组名称", name = "groupName")
	private String groupName;
    /**
	 * 用户 id
	 * */
    @TableField(value = "USER_ID")
	@ApiModelProperty(value = "用户 id", name = "userId")
	private Long userId;

}
