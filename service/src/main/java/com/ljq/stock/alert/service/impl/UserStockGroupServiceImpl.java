package com.ljq.stock.alert.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljq.stock.alert.common.api.ApiMsgEnum;
import com.ljq.stock.alert.common.api.ApiResult;
import com.ljq.stock.alert.dao.StockGroupStockDao;
import com.ljq.stock.alert.dao.UserStockGroupDao;
import com.ljq.stock.alert.model.entity.StockGroupStockEntity;
import com.ljq.stock.alert.model.entity.UserStockGroupEntity;
import com.ljq.stock.alert.model.param.userstockgroup.*;
import com.ljq.stock.alert.model.vo.UserTokenVo;
import com.ljq.stock.alert.service.UserStockGroupService;
import com.ljq.stock.alert.service.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 用户股票分组业务层具体实现类
 *
 * @author junqiang.lu
 * @date 2021-12-21 11:21:27
 */
@Slf4j
@Service("userStockGroupService")
@Transactional(rollbackFor = {Exception.class})
public class UserStockGroupServiceImpl implements UserStockGroupService {

	@Autowired
	private UserStockGroupDao userStockGroupDao;
	@Autowired
	private StockGroupStockDao stockGroupStockDao;

	/**
	 * 保存(单条)
	 *
	 * @param userStockGroupSaveParam
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public ApiResult save(UserStockGroupSaveParam userStockGroupSaveParam) {
		// 获取用户信息
		UserTokenVo userTokenVo = SessionUtil.currentSession().getUserToken();
		// 请求参数获取
		UserStockGroupEntity userStockGroupParam = new UserStockGroupEntity();
		userStockGroupParam.setGroupName(userStockGroupSaveParam.getGroupName());
		userStockGroupParam.setUserId(userTokenVo.getId());
		// 保存
		userStockGroupDao.insert(userStockGroupParam);
		return ApiResult.success(userStockGroupParam);
	}

	/**
	 * 查询详情(单条)
	 *
	 * @param userStockGroupInfoParam
	 * @return
	 */
	@Override
	public ApiResult info(UserStockGroupInfoParam userStockGroupInfoParam) {
		UserStockGroupEntity userStockGroup = userStockGroupDao.selectById(userStockGroupInfoParam.getId());
		UserTokenVo userTokenVo = SessionUtil.currentSession().getUserToken();
		if (Objects.isNull(userStockGroup) || !Objects.equals(userTokenVo.getId(), userStockGroup.getUserId())) {
			return ApiResult.fail(ApiMsgEnum.USER_STOCK_GROUP_NOT_EXIST);
		}
		return ApiResult.success(userStockGroup);
	}

	/**
	 * 查询列表
	 *
	 * @param userStockGroupListParam
	 * @return
	 */
	@Override
	public ApiResult list(UserStockGroupListParam userStockGroupListParam) {
		UserTokenVo userTokenVo = SessionUtil.currentSession().getUserToken();
		// 分页查询
		LambdaQueryWrapper<UserStockGroupEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(UserStockGroupEntity::getUserId, userTokenVo.getId())
				.like(StrUtil.isNotBlank(userStockGroupListParam.getGroupName()),UserStockGroupEntity::getGroupName,
						userStockGroupListParam.getGroupName());
		IPage<UserStockGroupEntity> page = userStockGroupDao.selectPage(new Page<>(
				userStockGroupListParam.getCurrentPage(), userStockGroupListParam.getPageSize()), queryWrapper);
		return ApiResult.success(page);
	}

	/**
	 * 更新(单条)
	 *
	 * @param userStockGroupUpdateParam
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public ApiResult update(UserStockGroupUpdateParam userStockGroupUpdateParam) {
		// 请求参数获取
		UserTokenVo userTokenVo = SessionUtil.currentSession().getUserToken();
		UserStockGroupEntity userStockGroupParam = new UserStockGroupEntity();
		BeanUtil.copyProperties(userStockGroupUpdateParam, userStockGroupParam, CopyOptions.create().ignoreNullValue());
		userStockGroupParam.setUserId(userTokenVo.getId());
		int count = userStockGroupDao.updateById(userStockGroupParam);
		if (count < 1) {
			return ApiResult.fail(ApiMsgEnum.USER_STOCK_GROUP_NOT_EXIST);
		}
		return ApiResult.success(userStockGroupParam);
	}

	/**
	 * 删除(单条)
	 *
	 * @param userStockGroupDeleteParam
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public ApiResult delete(UserStockGroupDeleteParam userStockGroupDeleteParam) {
		// 请求参数获取
		UserTokenVo userTokenVo = SessionUtil.currentSession().getUserToken();
		LambdaQueryWrapper<UserStockGroupEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(UserStockGroupEntity::getId, userStockGroupDeleteParam.getId())
				.eq(UserStockGroupEntity::getUserId, userTokenVo.getId());
		// 更新对象
		int count = userStockGroupDao.delete(queryWrapper);
		if (count < 1) {
			return ApiResult.fail(ApiMsgEnum.USER_STOCK_GROUP_NOT_EXIST);
		}
		// 删除分组内关联股票
		LambdaQueryWrapper<StockGroupStockEntity> stockGroupStockQueryWrapper = Wrappers.lambdaQuery();
		stockGroupStockQueryWrapper.eq(StockGroupStockEntity::getStockGroupId,
				userStockGroupDeleteParam.getId());
		stockGroupStockDao.delete(stockGroupStockQueryWrapper);
		return ApiResult.success();
	}
	

	
}
