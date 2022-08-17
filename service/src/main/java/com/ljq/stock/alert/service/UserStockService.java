package com.ljq.stock.alert.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljq.stock.alert.model.entity.UserStockEntity;
import com.ljq.stock.alert.model.param.userstock.*;

/**
 * 用户股票业务层接口
 * 
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
public interface UserStockService {

	/**
     * 新增(单条)
     *
     * @param saveParam
     * @return
     */
	UserStockEntity save(UserStockSaveParam saveParam);

	/**
     * 查询详情(单条)
     *
     * @param infoParam
     * @return
     */
	UserStockEntity info(UserStockInfoParam infoParam);

	/**
	 * 查询详情(单条)-后台管理
	 *
	 * @param infoParam
	 * @return
	 */
	UserStockEntity infoAdmin(UserStockInfoParam infoParam);

	/**
     * 分页查询
     *
     * @param listParam
     * @return
     */
	IPage<UserStockEntity> page(UserStockListParam listParam);

	/**
	 * 分页查询-后台管理
	 *
	 * @param listAdminParam
	 * @return
	 */
	IPage<UserStockEntity> pageAdmin(UserStockListAdminParam listAdminParam);

	/**
     * 更新(单条)
     *
     * @param updateParam
     * @return
     */
	void update(UserStockUpdateParam updateParam);

	/**
     * 删除(单条)
     *
     * @param deleteParam
     * @return
     */
	void delete(UserStockDeleteParam deleteParam);

	/**
     * 批量删除
     *
     * @param deleteBatchParam
     * @return
     */
	void deleteBatch(UserStockDeleteBatchParam deleteBatchParam);



}
