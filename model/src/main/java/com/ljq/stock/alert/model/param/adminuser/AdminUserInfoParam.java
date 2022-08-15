package com.ljq.stock.alert.model.param.adminuser;

import com.ljq.stock.alert.model.BaseInfoParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 管理员用户参数接收类
 *
 * @author junqiang.lu
 * @date 2022-08-15 11:08:13
 */
@Data
@ApiModel(value = "管理员用户查询详情(单条)", description = "管理员用户查询详情(单条)")
public class AdminUserInfoParam extends BaseInfoParam {

    private static final long serialVersionUID = 7529876181657038561L;

}
