package com.ljq.stock.alert.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljq.stock.alert.dao.UserInfoDao;
import com.ljq.stock.alert.model.entity.UserInfoEntity;
import com.ljq.stock.alert.model.param.user.*;
import com.ljq.stock.alert.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户信息业务层具体实现类
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Service("userInfoService")
@Transactional(rollbackFor = {Exception.class})
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoDao userInfoDao;

	/**
	 * 保存(单条)
	 *
	 * @param saveParam
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public UserInfoEntity save(UserInfoSaveParam saveParam) {
		// 请求参数获取
		UserInfoEntity userInfoParam = new UserInfoEntity();
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
	public UserInfoEntity info(UserInfoInfoParam infoParam) {
		// 请求参数获取
		UserInfoEntity userInfoParam = new UserInfoEntity();
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
	public IPage<UserInfoEntity> page(UserInfoListParam listParam) {
		// 请求参数获
		UserInfoEntity userInfoParam = new UserInfoEntity();
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
	public void update(UserInfoUpdateParam updateParam) {
		// 请求参数获取
		UserInfoEntity userInfoParam = new UserInfoEntity();
		userInfoParam.setId(updateParam.getId());

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
	public void delete(UserInfoDeleteParam deleteParam) {
		// 请求参数获取
		UserInfoEntity userInfoParam = new UserInfoEntity();
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
	public void deleteBatch(UserInfoDeleteBatchParam deleteBatchParam) {
		// 请求参数获取
			UserInfoEntity userInfoParam = new UserInfoEntity();
		// 判断对象是否存在

		// 更新对象

	}



}
