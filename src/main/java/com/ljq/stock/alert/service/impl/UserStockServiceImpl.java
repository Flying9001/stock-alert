package com.ljq.stock.alert.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljq.stock.alert.dao.UserStockDao;
import com.ljq.stock.alert.model.entity.UserStockEntity;
import com.ljq.stock.alert.model.param.userstock.*;
import com.ljq.stock.alert.service.UserStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户股票业务层具体实现类
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Service("userStockService")
@Transactional(rollbackFor = {Exception.class})
@Slf4j
public class UserStockServiceImpl implements UserStockService {

	@Autowired
	private UserStockDao userStockDao;

	/**
	 * 保存(单条)
	 *
	 * @param saveParam
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public UserStockEntity save(UserStockSaveParam saveParam) {
		// 请求参数获取
		UserStockEntity userStockParam = new UserStockEntity();
		// 保存

		return null;
	}

	/**
	 * 查询详情(单条)
	 *
	 * @param infoParam
	 * @return
	 */
	@Override
	public UserStockEntity info(UserStockInfoParam infoParam) {
		// 请求参数获取
		UserStockEntity userStockParam = new UserStockEntity();
		// 查询

		return null;
	}

	/**
	 * 分页查询
	 *
	 * @param listParam
	 * @return
	 */
	@Override
	public IPage<UserStockEntity> page(UserStockListParam listParam) {
		// 请求参数获
		UserStockEntity userStockParam = new UserStockEntity();
		// 分页查询


		return null;
	}

	/**
	 * 更新(单条)
	 *
	 * @param updateParam
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void update(UserStockUpdateParam updateParam) {
		// 请求参数获取
		UserStockEntity userStockParam = new UserStockEntity();
		userStockParam.setId(updateParam.getId());

		// 判断对象是否存在

		// 更新对象

	}

	/**
	 * 删除(单条)
	 *
	 * @param deleteParam
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void delete(UserStockDeleteParam deleteParam) {
		// 请求参数获取
		UserStockEntity userStockParam = new UserStockEntity();
		// 判断对象是否存在

		// 更新对象

	}

	/**
	 * 批量删除
	 *
	 * @param deleteBatchParam
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void deleteBatch(UserStockDeleteBatchParam deleteBatchParam) {
		// 请求参数获取
			UserStockEntity userStockParam = new UserStockEntity();
		// 判断对象是否存在

		// 更新对象

	}



}
