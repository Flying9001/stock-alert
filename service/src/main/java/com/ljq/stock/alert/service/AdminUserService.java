package com.ljq.stock.alert.service;

import com.ljq.stock.alert.common.api.ApiResult;
import com.ljq.stock.alert.model.param.adminuser.*;

/**
 * 管理员用户业务层接口
 * 
 * @author junqiang.lu
 * @date 2022-08-15 11:08:13
 */
public interface AdminUserService {

	/**
     * 保存(单条)
     *
     * @param adminUserSaveParam
     * @return
     */
	ApiResult save(AdminUserSaveParam adminUserSaveParam);

	/**
     * 查询详情(单条)
     *
     * @param adminUserInfoParam
     * @return
     */
	ApiResult info(AdminUserInfoParam adminUserInfoParam);

	/**
     * 查询列表
     *
     * @param adminUserListParam
     * @return
     */
	ApiResult list(AdminUserListParam adminUserListParam);

	/**
     * 更新(单条)
     *
     * @param adminUserUpdateParam
     * @return
     */
	ApiResult update(AdminUserUpdateParam adminUserUpdateParam);

	/**
     * 删除(单条)
     *
     * @param adminUserDeleteParam
     * @return
     */
	ApiResult delete(AdminUserDeleteParam adminUserDeleteParam);


}
