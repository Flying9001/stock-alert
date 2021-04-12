package com.ljq.stock.alert.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljq.stock.alert.common.api.ApiMsgEnum;
import com.ljq.stock.alert.common.config.StockApiConfig;
import com.ljq.stock.alert.common.exception.CommonException;
import com.ljq.stock.alert.common.util.StockUtil;
import com.ljq.stock.alert.dao.StockSourceDao;
import com.ljq.stock.alert.dao.UserStockDao;
import com.ljq.stock.alert.model.entity.StockSourceEntity;
import com.ljq.stock.alert.model.entity.UserStockEntity;
import com.ljq.stock.alert.model.param.stocksource.*;
import com.ljq.stock.alert.service.StockSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Objects;

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
	@Autowired
	private UserStockDao userStockDao;
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
	public StockSourceEntity save(StockSourceSaveParam saveParam) {
		// 校验-股票是否已入库
		StockSourceEntity stockSourceDB = stockSourceDao.selectOne(Wrappers.lambdaQuery(new StockSourceEntity())
				.eq(StockSourceEntity::getStockCode, saveParam.getStockCode())
				.eq(StockSourceEntity::getMarketType, saveParam.getMarketType()));
		if (Objects.nonNull(stockSourceDB)) {
			return stockSourceDB;
		}
		// 获取股票信息
		StockSourceEntity stockSourceParam = StockUtil.getStockFromSina(stockApiConfig, saveParam.getStockCode(),
				saveParam.getMarketType());
		// 保存
		stockSourceDao.insert(stockSourceParam);
		return stockSourceParam;
	}

	/**
	 * 查询详情(单条)
	 *
	 * @param infoParam
	 * @return
	 */
	@Override
	public StockSourceEntity info(StockSourceInfoParam infoParam) {
		return stockSourceDao.selectOne(Wrappers.lambdaQuery(new StockSourceEntity())
				.eq(StockSourceEntity::getMarketType, infoParam.getMarketType())
				.eq(StockSourceEntity::getStockCode, infoParam.getStockCode()));
	}

	/**
	 * 查询某一支股票实时数据
	 *
	 * @param infoRealTimeParam
	 * @return
	 * @throws IOException
	 * @throws CommonException
	 */
	@Override
	public StockSourceEntity infoRealTime(StockSourceInfoRealTimeParam infoRealTimeParam) {
		StockSourceEntity stockDB = info(infoRealTimeParam);
		if (Objects.isNull(stockDB)) {
			throw new CommonException(ApiMsgEnum.STOCK_QUERY_ERROR);
		}
		// 获取股票信息
		return StockUtil.getStockFromSina(stockApiConfig, infoRealTimeParam.getStockCode(),
				infoRealTimeParam.getMarketType());
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
		LambdaQueryWrapper<StockSourceEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(Objects.nonNull(listParam.getMarketType()), StockSourceEntity::getMarketType,
						listParam.getMarketType())
				.like(CharSequenceUtil.isNotBlank(listParam.getStockCode()), StockSourceEntity::getStockCode,
						listParam.getStockCode())
				.like(CharSequenceUtil.isNotBlank(listParam.getCompanyName()), StockSourceEntity::getCompanyName,
						listParam.getCompanyName());
		IPage<StockSourceEntity> page = new Page<>(listParam.getCurrentPage(), listParam.getPageSize());
		return stockSourceDao.selectPage(page, queryWrapper);
	}

	/**
	 * 分页查询实时股票数据
	 *
	 * @param listRealTimeParam
	 * @return
	 */
	@Override
	public IPage<StockSourceEntity> pageRealTime(StockSourceListRealTimeParam listRealTimeParam) {
		IPage<StockSourceEntity> page = page(listRealTimeParam);
		page.setRecords(StockUtil.getStocksFromSina(stockApiConfig, page.getRecords()));
		return page;
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
		// 判断-是否存在
		int countStock = stockSourceDao.selectCount(Wrappers.lambdaQuery(new StockSourceEntity())
				.eq(StockSourceEntity::getId, deleteParam.getId()));
		if (countStock < 1) {
			throw new CommonException(ApiMsgEnum.STOCK_QUERY_ERROR);
		}
		// 判断是否有用户添加关注
		int countUserStock = userStockDao.selectCount(Wrappers.lambdaQuery(new UserStockEntity())
				.eq(UserStockEntity::getStockId, deleteParam.getId()));
		if (countUserStock > 0) {
			throw new CommonException(ApiMsgEnum.STOCK_DELETE_ERROR_USER_HAS_FOLLOWED);
		}
		// 删除
		stockSourceDao.deleteById(deleteParam.getId());
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
		int countStock = stockSourceDao.selectCount(Wrappers.lambdaQuery(new StockSourceEntity())
				.in(StockSourceEntity::getId, deleteBatchParam.getIdList()));
		if (countStock < deleteBatchParam.getIdList().size()) {
			throw new CommonException(ApiMsgEnum.STOCK_QUERY_ERROR);
		}
		int countUserStock = userStockDao.selectCount(Wrappers.lambdaQuery(new UserStockEntity())
				.in(UserStockEntity::getStockId, deleteBatchParam.getIdList()));
		if (countUserStock > 0) {
			throw new CommonException(ApiMsgEnum.STOCK_DELETE_ERROR_USER_HAS_FOLLOWED);
		}
		// 删除对象
		stockSourceDao.deleteBatchIds(deleteBatchParam.getIdList());
	}





}
