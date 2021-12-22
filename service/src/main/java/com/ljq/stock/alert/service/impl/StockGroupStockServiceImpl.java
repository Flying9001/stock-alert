package com.ljq.stock.alert.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljq.stock.alert.common.api.ApiMsgEnum;
import com.ljq.stock.alert.common.api.ApiResult;
import com.ljq.stock.alert.common.component.RedisUtil;
import com.ljq.stock.alert.common.constant.CacheConst;
import com.ljq.stock.alert.common.util.CacheKeyUtil;
import com.ljq.stock.alert.dao.StockGroupStockDao;
import com.ljq.stock.alert.dao.StockSourceDao;
import com.ljq.stock.alert.dao.UserStockDao;
import com.ljq.stock.alert.dao.UserStockGroupDao;
import com.ljq.stock.alert.model.entity.StockGroupStockEntity;
import com.ljq.stock.alert.model.entity.StockSourceEntity;
import com.ljq.stock.alert.model.entity.UserStockGroupEntity;
import com.ljq.stock.alert.model.param.stockgroupstock.StockGroupStockDeleteParam;
import com.ljq.stock.alert.model.param.stockgroupstock.StockGroupStockInfoParam;
import com.ljq.stock.alert.model.param.stockgroupstock.StockGroupStockListParam;
import com.ljq.stock.alert.model.param.stockgroupstock.StockGroupStockSaveParam;
import com.ljq.stock.alert.model.vo.UserTokenVo;
import com.ljq.stock.alert.service.StockGroupStockService;
import com.ljq.stock.alert.service.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 用户股票分组关联股票业务层具体实现类
 *
 * @author junqiang.lu
 * @date 2021-12-21 11:21:28
 */
@Service("stockGroupStockService")
@Transactional(rollbackFor = {Exception.class})
@Slf4j
public class StockGroupStockServiceImpl implements StockGroupStockService {

	@Autowired
	private StockGroupStockDao stockGroupStockDao;
	@Autowired
	private UserStockGroupDao userStockGroupDao;
	@Autowired
	private UserStockDao userStockDao;
	@Autowired
	private StockSourceDao stockSourceDao;
	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 保存(单条)
	 *
	 * @param stockGroupStockSaveParam
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public ApiResult save(StockGroupStockSaveParam stockGroupStockSaveParam) {
		// 校验分组是否存在
		UserTokenVo userTokenVo = SessionUtil.currentSession().getUserToken();
		LambdaQueryWrapper<UserStockGroupEntity> stockGroupWrapper = Wrappers.lambdaQuery();
		stockGroupWrapper.eq(UserStockGroupEntity::getId, stockGroupStockSaveParam.getStockGroupId())
				.eq(UserStockGroupEntity::getUserId, userTokenVo.getId());
		int countGroup = userStockGroupDao.selectCount(stockGroupWrapper);
		if (countGroup < 1) {
			return ApiResult.fail(ApiMsgEnum.USER_STOCK_GROUP_NOT_EXIST);
		}
		// 校验股票是否存在
		LambdaQueryWrapper<StockSourceEntity> stockWrapper = Wrappers.lambdaQuery();
		stockWrapper.eq(StockSourceEntity::getId, stockGroupStockSaveParam.getStockId());
		int countStock = stockSourceDao.selectCount(stockWrapper);
		if (countStock < 1) {
			return ApiResult.fail(ApiMsgEnum.STOCK_QUERY_ERROR);
		}
		// 校验是否重复添加
		LambdaQueryWrapper<StockGroupStockEntity> stockGroupStockWrapper = Wrappers.lambdaQuery();
		stockGroupStockWrapper.eq(StockGroupStockEntity::getStockGroupId, stockGroupStockSaveParam.getStockGroupId())
				.eq(StockGroupStockEntity::getStockId, stockGroupStockSaveParam.getStockId());
		int countGroupStock = stockGroupStockDao.selectCount(stockGroupStockWrapper);
		if (countGroupStock > 0) {
			return ApiResult.fail(ApiMsgEnum.STOCK_GROUP_STOCK_EXISTED);
		}
		// 请求参数获取
		StockGroupStockEntity stockGroupStockParam = new StockGroupStockEntity();
		BeanUtil.copyProperties(stockGroupStockSaveParam,stockGroupStockParam,CopyOptions.create()
				.ignoreError().ignoreNullValue());
		// 保存
		stockGroupStockDao.insert(stockGroupStockParam);
		return ApiResult.success(stockGroupStockParam);
	}

	/**
	 * 查询详情(单条)
	 *
	 * @param stockGroupStockInfoParam
	 * @return
	 */
	@Override
	public ApiResult info(StockGroupStockInfoParam stockGroupStockInfoParam) {
		StockGroupStockEntity stockGroupStock = stockGroupStockDao.queryObject(stockGroupStockInfoParam.getId());
		if (Objects.isNull(stockGroupStock)) {
			return ApiResult.fail(ApiMsgEnum.STOCK_GROUP_STOCK_NOT_EXIST);
		}
		// 股票实时数据
		stockGroupStock.setStockSource(redisUtil.mapGet(CacheConst.CACHE_KEY_STOCK_SOURCE_ALL,
				CacheKeyUtil.createStockSourceKey(stockGroupStock.getStockSource().getMarketType(),
						stockGroupStock.getStockSource().getStockCode()), StockSourceEntity.class));
		return ApiResult.success(stockGroupStock);
	}

	/**
	 * 查询列表
	 *
	 * @param stockGroupStockListParam
	 * @return
	 */
	@Override
	public ApiResult list(StockGroupStockListParam stockGroupStockListParam) {
		IPage<StockGroupStockEntity> page = stockGroupStockDao.queryPage(BeanUtil.beanToMap(stockGroupStockListParam),
				new Page<>(stockGroupStockListParam.getCurrentPage(), stockGroupStockListParam.getPageSize()));
		if (CollUtil.isEmpty(page.getRecords())) {
			return ApiResult.success(page);
		}
		// 获取实时股票数据
		page.getRecords().stream().forEach(stockGroupStock ->
			stockGroupStock.setStockSource(redisUtil.mapGet(CacheConst.CACHE_KEY_STOCK_SOURCE_ALL,
					CacheKeyUtil.createStockSourceKey(stockGroupStock.getStockSource().getMarketType(),
							stockGroupStock.getStockSource().getStockCode()), StockSourceEntity.class))
		);
		return ApiResult.success(page);
	}

	/**
	 * 删除(单条)
	 *
	 * @param stockGroupStockDeleteParam
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public ApiResult delete(StockGroupStockDeleteParam stockGroupStockDeleteParam) {
		int count = stockGroupStockDao.deleteById(stockGroupStockDeleteParam.getId());
		if (count < 1) {
			return ApiResult.fail(ApiMsgEnum.STOCK_GROUP_STOCK_NOT_EXIST);
		}
		return ApiResult.success();
	}
	

	
}
