package com.ljq.stock.alert.model.param.userstock;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 用户股票删除(单条)
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Data
@ApiModel(value = "用户股票删除(单条)", description = "用户股票删除(单条)")
public class UserStockDeleteParam extends UserStockInfoParam {

    private static final long serialVersionUID = 1L;

}
