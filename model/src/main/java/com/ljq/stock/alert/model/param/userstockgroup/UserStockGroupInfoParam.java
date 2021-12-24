package com.ljq.stock.alert.model.param.userstockgroup;

import com.ljq.stock.alert.model.BaseInfoParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 用户股票分组参数接收类
 *
 * @author junqiang.lu
 * @date 2021-12-21 11:21:27
 */
@Data
@ApiModel(value = "用户股票分组查询详情(单条)", description = "用户股票分组查询详情(单条)")
public class UserStockGroupInfoParam extends BaseInfoParam {

    private static final long serialVersionUID = 1L;


}
