package com.ljq.stock.alert.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljq.stock.alert.common.api.ApiMsgEnum;
import com.ljq.stock.alert.common.component.RedisUtil;
import com.ljq.stock.alert.common.constant.CacheConst;
import com.ljq.stock.alert.common.exception.CommonException;
import com.ljq.stock.alert.common.util.CacheKeyUtil;
import com.ljq.stock.alert.dao.StockSourceDao;
import com.ljq.stock.alert.dao.UserStockDao;
import com.ljq.stock.alert.model.entity.StockSourceEntity;
import com.ljq.stock.alert.model.entity.UserStockEntity;
import com.ljq.stock.alert.model.param.userstock.*;
import com.ljq.stock.alert.model.vo.UserTokenVo;
import com.ljq.stock.alert.service.UserStockService;
import com.ljq.stock.alert.service.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
	private RedisUtil redisUtil;


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
		int countStock = stockSourceDao.selectCount(Wrappers.lambdaQuery(new StockSourceEntity())
				.eq(StockSourceEntity::getId, saveParam.getStockId()));
		if (countStock < 1) {
			throw new CommonException(ApiMsgEnum.STOCK_QUERY_ERROR);
		}
		// 校验-是否重复添加
		UserTokenVo userTokenVo = SessionUtil.currentSession().getUserToken();
		int countUserStock = userStockDao.selectCount(Wrappers.lambdaQuery(new UserStockEntity())
				.eq(UserStockEntity::getStockId, saveParam.getStockId())
				.eq(UserStockEntity::getUserId, userTokenVo.getId()));
		if (countUserStock > 0) {
			throw new CommonException(ApiMsgEnum.USER_STOCK_EXISTED);
		}
		// 请求参数获取
		UserStockEntity userStockParam = new UserStockEntity();
		BeanUtil.copyProperties(saveParam, userStockParam, CopyOptions.create().ignoreNullValue());
		userStockParam.setUserId(userTokenVo.getId());
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
		UserTokenVo userTokenVo = SessionUtil.currentSession().getUserToken();
		UserStockEntity userStockDB = userStockDao.selectOne(Wrappers.lambdaQuery(new UserStockEntity())
				.eq(UserStockEntity::getId, infoParam.getId())
				.eq(UserStockEntity::getUserId, userTokenVo.getId()));
		if (Objects.isNull(userStockDB)) {
			return new UserStockEntity();
		}
		// 查询股票信息
		StockSourceEntity stockDB = stockSourceDao.selectById(userStockDB.getStockId());
		// 查询实时股价
		userStockDB.setStockSource(redisUtil.mapGet(CacheConst.CACHE_KEY_STOCK_SOURCE_ALL,
				CacheKeyUtil.createStockSourceKey(stockDB.getMarketType(), stockDB.getStockCode()),
				StockSourceEntity.class));
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
		UserTokenVo userTokenVo = SessionUtil.currentSession().getUserToken();
		Map<String, Object> queryMap = BeanUtil.beanToMap(listParam);
		queryMap.put("userId", userTokenVo.getId());
		IPage<UserStockEntity> page = userStockDao.queryPage(queryMap,
				new Page<>(listParam.getCurrentPage(), listParam.getPageSize()));
		if (CollUtil.isEmpty(page.getRecords())) {
			return page;
		}
        // 获取用户关注股票的实时价格
		page.getRecords().stream().forEach(userStock ->
				userStock.setStockSource(redisUtil.mapGet(CacheConst.CACHE_KEY_STOCK_SOURCE_ALL,
						CacheKeyUtil.createStockSourceKey(userStock.getStockSource().getMarketType(),
								userStock.getStockSource().getStockCode()), StockSourceEntity.class)));
		return page;
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
		UserTokenVo userTokenVo = SessionUtil.currentSession().getUserToken();
		int count = userStockDao.selectCount(Wrappers.lambdaQuery(new UserStockEntity())
				.eq(UserStockEntity::getId, updateParam.getId())
				.eq(UserStockEntity::getUserId, userTokenVo.getId()));
		if (count < 1) {
			throw new CommonException(ApiMsgEnum.USER_STOCK_NOT_EXIST);
		}
		// 请求参数获取
		UserStockEntity userStockParam = new UserStockEntity();
		BeanUtil.copyProperties(updateParam, userStockParam, CopyOptions.create().ignoreNullValue());
		userStockParam.setUserId(userTokenVo.getId());
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
		UserTokenVo userTokenVo = SessionUtil.currentSession().getUserToken();
		LambdaQueryWrapper<UserStockEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(UserStockEntity::getId, deleteParam.getId())
				.eq(UserStockEntity::getUserId, userTokenVo.getId());
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
		UserTokenVo userTokenVo = SessionUtil.currentSession().getUserToken();
		LambdaQueryWrapper<UserStockEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(UserStockEntity::getUserId, userTokenVo.getId())
				.in(UserStockEntity::getId, deleteBatchParam.getIdList());
		List<UserStockEntity> userStockDBList = userStockDao.selectList(queryWrapper);
		if (CollectionUtil.isEmpty(userStockDBList) || userStockDBList.size() < deleteBatchParam.getIdList().size()) {
			throw new CommonException(ApiMsgEnum.USER_STOCK_NOT_EXIST);
		}
		// 删除对象
		userStockDao.delete(queryWrapper);
	}



}
