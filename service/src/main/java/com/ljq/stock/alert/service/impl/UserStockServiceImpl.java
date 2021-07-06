package com.ljq.stock.alert.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljq.stock.alert.common.api.ApiMsgEnum;
import com.ljq.stock.alert.common.config.StockApiConfig;
import com.ljq.stock.alert.common.exception.CommonException;
import com.ljq.stock.alert.dao.StockSourceDao;
import com.ljq.stock.alert.dao.UserInfoDao;
import com.ljq.stock.alert.dao.UserStockDao;
import com.ljq.stock.alert.model.entity.StockSourceEntity;
import com.ljq.stock.alert.model.entity.UserInfoEntity;
import com.ljq.stock.alert.model.entity.UserStockEntity;
import com.ljq.stock.alert.model.param.userstock.*;
import com.ljq.stock.alert.service.UserStockService;
import com.ljq.stock.alert.service.util.StockUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
	@Autowired
	private StockSourceDao stockSourceDao;
	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired
	private StockApiConfig stockApiConfig;


	/**
	 * 保存(单条)
	 *
	 * @param saveParam
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public UserStockEntity save(UserStockSaveParam saveParam) {
		// 校验-股票是否存在
		StockSourceEntity stockDB = stockSourceDao.selectOne(Wrappers.lambdaQuery(new StockSourceEntity())
				.eq(StockSourceEntity::getStockCode, saveParam.getStockCode())
				.eq(StockSourceEntity::getMarketType, saveParam.getMarketType()));
		if (Objects.isNull(stockDB)) {
			throw new CommonException(ApiMsgEnum.STOCK_QUERY_ERROR);
		}
		// 校验-用户是否存在
		int countUser = userInfoDao.selectCount(Wrappers.lambdaQuery(new UserInfoEntity())
				.eq(UserInfoEntity::getId, saveParam.getUserId()));
		if (countUser < 1) {
			throw new CommonException(ApiMsgEnum.USER_ACCOUNT_NOT_EXIST);
		}
		// 校验-是否重复添加
		int countUserStock = userStockDao.selectCount(Wrappers.lambdaQuery(new UserStockEntity())
				.eq(UserStockEntity::getStockId, stockDB.getId())
				.eq(UserStockEntity::getUserId, saveParam.getUserId()));
		if (countUserStock > 0) {
			throw new CommonException(ApiMsgEnum.USER_STOCK_EXISTED);
		}
		// 请求参数获取
		UserStockEntity userStockParam = new UserStockEntity();
		BeanUtil.copyProperties(saveParam, userStockParam, CopyOptions.create().ignoreNullValue());
		userStockParam.setStockId(stockDB.getId());
		// 保存
		userStockDao.insert(userStockParam);
		return userStockParam;
	}

	/**
	 * 查询详情(单条)
	 *
	 * @param infoParam
	 * @return
	 */
	@Override
	public UserStockEntity info(UserStockInfoParam infoParam) {
		// 校验-用户股票是否存在
		UserStockEntity userStockDB = userStockDao.selectOne(Wrappers.lambdaQuery(new UserStockEntity())
				.eq(UserStockEntity::getId, infoParam.getId())
				.eq(UserStockEntity::getUserId, infoParam.getUserId()));
		if (Objects.isNull(userStockDB)) {
			return new UserStockEntity();
		}
		// 查询股票信息
		StockSourceEntity stockDB = stockSourceDao.selectById(userStockDB.getStockId());
		// 查询实时股价
		userStockDB.setStockSource(StockUtil.getStockFromSina(stockApiConfig, stockDB.getStockCode(),
				stockDB.getMarketType()));
		return userStockDB;
	}

	/**
	 * 分页查询
	 *
	 * @param listParam
	 * @return
	 */
	@Override
	public IPage<UserStockEntity> page(UserStockListParam listParam) {
		IPage<StockSourceEntity> stockPage = new Page<>(listParam.getCurrentPage(), listParam.getPageSize());
		Map<String, Object> paramMap = BeanUtil.beanToMap(listParam);
		stockPage = stockSourceDao.queryPageByUser(stockPage, paramMap);
		IPage<UserStockEntity> userStockPage = new Page<>(listParam.getCurrentPage(), listParam.getPageSize());
		if (stockPage.getTotal() < 1) {
			return userStockPage;
		}
		List<Long> stockIdList = new ArrayList<>();
		stockPage.getRecords().stream().forEach(stock -> stockIdList.add(stock.getId()));
		stockPage.setRecords(StockUtil.getStocksFromSina(stockApiConfig, stockPage.getRecords()));
		BeanUtil.copyProperties(stockPage, userStockPage, CopyOptions.create().ignoreNullValue().ignoreError());
		List<UserStockEntity> userStockList = userStockDao.selectList(Wrappers.lambdaQuery(new UserStockEntity())
				.in(UserStockEntity::getStockId, stockIdList));
		for (int i = 0; i < userStockList.size(); i++) {
			userStockList.get(i).setStockSource(stockPage.getRecords().get(i));
		}
		userStockPage.setRecords(userStockList);
		return userStockPage;
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
		// 校验-是否存在
		int count = userStockDao.selectCount(Wrappers.lambdaQuery(new UserStockEntity())
				.eq(UserStockEntity::getId, updateParam.getId())
				.eq(UserStockEntity::getUserId, updateParam.getUserId()));
		if (count < 1) {
			throw new CommonException(ApiMsgEnum.USER_STOCK_NOT_EXIST);
		}
		// 请求参数获取
		UserStockEntity userStockParam = new UserStockEntity();
		BeanUtil.copyProperties(updateParam, userStockParam, CopyOptions.create().ignoreNullValue());
		// 更新对象
		userStockDao.updateById(userStockParam);
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
		// 判断对象是否存在
		LambdaQueryWrapper<UserStockEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(UserStockEntity::getId, deleteParam.getId())
				.eq(UserStockEntity::getUserId, deleteParam.getUserId());
		UserStockEntity userStockParam = userStockDao.selectOne(queryWrapper);
		if (Objects.isNull(userStockParam)) {
			throw new CommonException(ApiMsgEnum.USER_STOCK_NOT_EXIST);
		}
		// 删除对象
		userStockDao.delete(queryWrapper);
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
		// 判断对象是否存在
		LambdaQueryWrapper<UserStockEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(UserStockEntity::getUserId, deleteBatchParam.getUserId())
				.in(UserStockEntity::getId, deleteBatchParam.getIdList());
		List<UserStockEntity> userStockDBList = userStockDao.selectList(queryWrapper);
		if (CollectionUtil.isEmpty(userStockDBList) || userStockDBList.size() < deleteBatchParam.getIdList().size()) {
			throw new CommonException(ApiMsgEnum.USER_STOCK_NOT_EXIST);
		}
		// 删除对象
		userStockDao.delete(queryWrapper);
	}



}
