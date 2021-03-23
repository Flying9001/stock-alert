package com.ljq.stock.alert.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljq.stock.alert.dao.StockSourceDao;
import com.ljq.stock.alert.model.entity.StockSourceEntity;
import com.ljq.stock.alert.model.param.stocksource.*;
import com.ljq.stock.alert.service.StockSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 股票源业务层具体实现类
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Service("stockSourceService")
@Transactional(rollbackFor = {Exception.class})
@Slf4j
public class StockSourceServiceImpl implements StockSourceService {

	@Autowired
	private StockSourceDao stockSourceDao;

	/**
	 * 保存(单条)
	 *
	 * @param saveParam
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public StockSourceEntity save(StockSourceSaveParam saveParam) {
		// 请求参数获取
		StockSourceEntity stockSourceParam = new StockSourceEntity();
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
	public StockSourceEntity info(StockSourceInfoParam infoParam) {
		// 请求参数获取
		StockSourceEntity stockSourceParam = new StockSourceEntity();
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
	public IPage<StockSourceEntity> page(StockSourceListParam listParam) {
		// 请求参数获
		StockSourceEntity stockSourceParam = new StockSourceEntity();
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
	public void update(StockSourceUpdateParam updateParam) {
		// 请求参数获取
		StockSourceEntity stockSourceParam = new StockSourceEntity();
		stockSourceParam.setId(updateParam.getId());

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
	public void delete(StockSourceDeleteParam deleteParam) {
		// 请求参数获取
		StockSourceEntity stockSourceParam = new StockSourceEntity();
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
	public void deleteBatch(StockSourceDeleteBatchParam deleteBatchParam) {
		// 请求参数获取
			StockSourceEntity stockSourceParam = new StockSourceEntity();
		// 判断对象是否存在

		// 更新对象

	}



}
