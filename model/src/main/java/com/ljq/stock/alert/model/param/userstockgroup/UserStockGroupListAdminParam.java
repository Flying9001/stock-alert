package com.ljq.stock.alert.model.param.userstockgroup;

import com.ljq.stock.alert.model.BasePageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 用户股票分组参数接收类
 *
 * @author junqiang.lu
 * @date 2021-12-21 11:21:27
 */
@Data
@ApiModel(value = "用户股票分组查询列表-后台管理", description = "用户股票分组查询列表-后台管理")
public class UserStockGroupListAdminParam extends BasePageParam {

    private static final long serialVersionUID = 1L;

    /**
     * 用户 id
     */
    @ApiModelProperty(value = "用户 id", name = "userId", example = "1")
    private Long userId;

    /**
     * 分组名称
     * */
    @Length(max = 30, message = "分组名称需要控制在 30 字符以内")
    @ApiModelProperty(value = "分组名称", name = "groupName")
    private String groupName;

}
