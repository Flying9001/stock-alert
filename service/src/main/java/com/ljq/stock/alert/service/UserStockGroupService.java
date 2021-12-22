package com.ljq.stock.alert.service;

import com.ljq.stock.alert.common.api.ApiResult;
import com.ljq.stock.alert.model.param.userstockgroup.*;

/**
 * 用户股票分组业务层接口
 * 
 * @author junqiang.lu
 * @date 2021-12-21 11:21:27
 */
public interface UserStockGroupService {

	/**
     * 保存(单条)
     *
     * @param userStockGroupSaveParam
     * @return
     */
	ApiResult save(UserStockGroupSaveParam userStockGroupSaveParam);

	/**
     * 查询详情(单条)
     *
     * @param userStockGroupInfoParam
     * @return
     */
	ApiResult info(UserStockGroupInfoParam userStockGroupInfoParam);

	/**
     * 查询列表
     *
     * @param userStockGroupListParam
     * @return
     */
	ApiResult list(UserStockGroupListParam userStockGroupListParam);

	/**
     * 更新(单条)
     *
     * @param userStockGroupUpdateParam
     * @return
     */
	ApiResult update(UserStockGroupUpdateParam userStockGroupUpdateParam);

	/**
     * 删除(单条)
     *
     * @param userStockGroupDeleteParam
     * @return
     */
	ApiResult delete(UserStockGroupDeleteParam userStockGroupDeleteParam);


}
