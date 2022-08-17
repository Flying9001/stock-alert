package com.ljq.stock.alert.model.param.userstock;

import com.ljq.stock.alert.model.param.stocksource.StockSourceListParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Description: 用户股票分页查询-后台管理
 * @Author: junqiang.lu
 * @Date: 2022/8/17
 */
@Data
@ToString(callSuper = true)
@ApiModel(value = "用户股票分页查询-后台管理", description = "用户股票分页查询-后台管理")
public class UserStockListAdminParam extends StockSourceListParam {

    private static final long serialVersionUID = 6067460882818857932L;

    /**
     * 用户 id
     */
    @ApiModelProperty(value = "用户 id", name = "userId", example = "1")
    private Long userId;

}
