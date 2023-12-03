package com.ljq.stock.alert.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljq.stock.alert.common.api.ApiMsgEnum;
import com.ljq.stock.alert.common.api.ApiResult;
import com.ljq.stock.alert.common.component.RedisUtil;
import com.ljq.stock.alert.common.config.StockApiConfig;
import com.ljq.stock.alert.common.constant.StockConst;
import com.ljq.stock.alert.common.exception.CommonException;
import com.ljq.stock.alert.common.util.CacheKeyUtil;
import com.ljq.stock.alert.dao.StockSourceDao;
import com.ljq.stock.alert.dao.UserStockDao;
import com.ljq.stock.alert.model.entity.StockSourceEntity;
import com.ljq.stock.alert.model.entity.UserStockEntity;
import com.ljq.stock.alert.model.param.stocksource.*;
import com.ljq.stock.alert.model.vo.StockIndexVo;
import com.ljq.stock.alert.service.StockSourceService;
import com.ljq.stock.alert.service.util.StockUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ThreadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 股票源业务层具体实现类
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Service("stockSourceService")
@Transactional(rollbackFor = {Exception.class})
@Slf4j
public class StockSourceServiceImpl extends ServiceImpl<StockSourceDao, StockSourceEntity>
		implements StockSourceService {

	@Autowired
	private StockSourceDao stockSourceDao;
	@Autowired
	private UserStockDao userStockDao;
	@Autowired
	private StockApiConfig stockApiConfig;
	@Autowired
	private RedisUtil redisUtil;

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
		StockSourceEntity stockSourceParam = StockUtil.getStockLive(stockApiConfig, saveParam.getStockCode(),
				saveParam.getMarketType());
		// 保存
		stockSourceDao.insert(stockSourceParam);
		// 存入缓存
		redisUtil.mapPut(StockConst.CACHE_KEY_STOCK_SOURCE_ALL, CacheKeyUtil.createStockSourceKey(stockSourceParam
						.getMarketType(), stockSourceParam.getStockCode()),
				stockSourceParam);
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
		// 从缓存中读取数据
		StockSourceEntity stockSource = redisUtil.mapGet(StockConst.CACHE_KEY_STOCK_SOURCE_ALL,
				CacheKeyUtil.createStockSourceKey(infoParam.getMarketType(), infoParam.getStockCode()),
				StockSourceEntity.class);
		if (Objects.nonNull(stockSource)) {
			return stockSource;
		}
		// 从数据库读取数据
		stockSource = stockSourceDao.selectOne(Wrappers.lambdaQuery(new StockSourceEntity())
				.eq(StockSourceEntity::getMarketType, infoParam.getMarketType())
				.eq(StockSourceEntity::getStockCode, infoParam.getStockCode()));
		// 存入缓存
		if (Objects.nonNull(stockSource)) {
			redisUtil.mapPut(StockConst.CACHE_KEY_STOCK_SOURCE_ALL, CacheKeyUtil.createStockSourceKey(
					stockSource.getMarketType(), stockSource.getStockCode()), stockSource);
		}
		return stockSource;
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
		// 获取实时股票信息
		StockSourceEntity stockRealTime = StockUtil.getStockLive(stockApiConfig, infoRealTimeParam.getStockCode(),
				infoRealTimeParam.getMarketType());
		BeanUtil.copyProperties(stockRealTime, stockDB, CopyOptions.create().ignoreError().ignoreNullValue());
		return stockDB;
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
		page.setRecords(StockUtil.getStocksLive(stockApiConfig, page.getRecords()));
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
		StockSourceEntity stockSource = stockSourceDao.selectOne(Wrappers.lambdaQuery(new StockSourceEntity())
				.eq(StockSourceEntity::getId, deleteParam.getId()));
		if (Objects.isNull(stockSource)) {
			throw new CommonException(ApiMsgEnum.STOCK_QUERY_ERROR);
		}
		// 判断是否有用户添加关注
		int countUserStock = userStockDao.selectCount(Wrappers.lambdaQuery(new UserStockEntity())
				.eq(UserStockEntity::getStockId, deleteParam.getId()));
		if (countUserStock > 0) {
			throw new CommonException(ApiMsgEnum.STOCK_DELETE_ERROR_USER_HAS_FOLLOWED);
		}
		// 缓存删除
		redisUtil.mapRemove(StockConst.CACHE_KEY_STOCK_SOURCE_ALL, CacheKeyUtil.createStockSourceKey(stockSource
				.getMarketType(), stockSource.getStockCode()));
		// 数据库删除
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
		List<StockSourceEntity> stockSourceList = stockSourceDao.selectList(Wrappers.lambdaQuery(new StockSourceEntity())
				.in(StockSourceEntity::getId, deleteBatchParam.getIdList()));
		if (CollUtil.isEmpty(stockSourceList) || stockSourceList.size() < deleteBatchParam.getIdList().size()) {
			throw new CommonException(ApiMsgEnum.STOCK_QUERY_ERROR);
		}
		int countUserStock = userStockDao.selectCount(Wrappers.lambdaQuery(new UserStockEntity())
				.in(UserStockEntity::getStockId, deleteBatchParam.getIdList()));
		if (countUserStock > 0) {
			throw new CommonException(ApiMsgEnum.STOCK_DELETE_ERROR_USER_HAS_FOLLOWED);
		}
		// 缓存删除
		redisUtil.mapRemoveBatch(StockConst.CACHE_KEY_STOCK_SOURCE_ALL, createStockCacheKeyList(stockSourceList));
		// 数据库删除
		stockSourceDao.deleteBatchIds(deleteBatchParam.getIdList());
	}

	/**
	 * 将所有数据库中的股票添加到缓存
	 *
	 * @return
	 */
	@Override
	public ApiResult<Void> allDbToCache() {
		List<StockSourceEntity> stockSourceDBList = stockSourceDao.selectList(Wrappers.emptyWrapper());
		if (CollUtil.isEmpty(stockSourceDBList)) {
			log.info("{}", "股票源中没有数据");
			return ApiResult.success();
		}
		Map<String, Object> stockSourceMap = new HashMap<>(16);
		stockSourceDBList.stream().forEach(stockSource ->
			stockSourceMap.put(CacheKeyUtil.createStockSourceKey(stockSource.getMarketType(),
					stockSource.getStockCode()), stockSource)
		);
		redisUtil.mapPutBatch(StockConst.CACHE_KEY_STOCK_SOURCE_ALL, stockSourceMap);
		return ApiResult.success();
	}

	/**
	 * 将缓存中的所有股票数据同步更新到数据库
	 *
	 * @return
	 */
	@Override
	public ApiResult<Void> allCacheToDb() {
		List<StockSourceEntity> stockSourceCacheList = redisUtil.mapGetAll(StockConst.CACHE_KEY_STOCK_SOURCE_ALL,
				StockSourceEntity.class);
		if (CollUtil.isEmpty(stockSourceCacheList)) {
			log.info("{}", "缓存中没有股票源数据");
			return ApiResult.success();
		}
		super.updateBatchById(stockSourceCacheList);
		return ApiResult.success();
	}

	/**
	 * 查询指数列表
	 *
	 * @return
	 */
	@Override
	public ApiResult<List<StockIndexVo>> queryIndexList() {
		return ApiResult.success(StockUtil.getStockIndexLive(stockApiConfig));
	}

	/**
	 * 初始化股票数据
	 *
	 * @param initDataParam
	 * @return
	 */
	@Override
	public ApiResult<Void> initStockData(StockSourceInitDataParam initDataParam) {
		// 防止频繁重复请求
		String initThreadName = "init-stock-thread";
		Collection<Thread> threads = ThreadUtils.findThreadsByName(initThreadName);
		if (CollUtil.isNotEmpty(threads)) {
			return ApiResult.fail(ApiMsgEnum.STOCK_INIT_REPEAT);
		}
		ThreadUtil.newThread(() -> {
			List<StockSourceEntity> stockNetList = null;
			switch (initDataParam.getDataSource().toLowerCase()) {
				case StockConst.STOCK_API_MYDATA:
					stockNetList = StockUtil.getAllStockFromMyData(stockApiConfig);
					break;
				default:
					stockNetList = StockUtil.getAllStockFromSina(stockApiConfig);
					break;
			}
			if (CollUtil.isEmpty(stockNetList)) {
				return;
			}
			// 查询本地股票数据
			List<StockSourceEntity> stockDbList = super.list();
			// 去除本地已经存在的股票数据
			Map<String, String> stockDbMap = stockDbList.stream().collect(Collectors
					.toMap(StockSourceEntity::getStockCode, StockSourceEntity::getCompanyName));
			List<StockSourceEntity> stockTempList = stockNetList.stream().filter(stockSourceEntity ->
					Objects.isNull(stockDbMap.get(stockSourceEntity.getStockCode()))).collect(Collectors.toList());
			log.info("未同步股票数量: {}", stockTempList.size());
			// 保存新股票
			if (CollUtil.isEmpty(stockTempList)) {
				return;
			}
			// 批量保存数据
			super.saveBatch(stockTempList);
			// 将数据保存到缓存中
			this.allDbToCache();
		}, initThreadName).start();
		return ApiResult.success();
	}

	/**
	 * 批量创建股票缓存 key
	 *
	 * @param stockSourceList
	 * @return
	 */
	private List<String> createStockCacheKeyList(List<StockSourceEntity> stockSourceList) {
		if (CollUtil.isEmpty(stockSourceList)) {
			return Collections.emptyList();
		}
		List<String> cacheKeyList = new ArrayList<>();
		stockSourceList.stream().forEach(stockSource ->
			cacheKeyList.add(CacheKeyUtil.createStockSourceKey(stockSource.getMarketType(), stockSource.getStockCode()))
		);
		return cacheKeyList;
	}





}
