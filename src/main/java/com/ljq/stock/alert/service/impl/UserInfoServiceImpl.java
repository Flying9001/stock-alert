package com.ljq.stock.alert.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljq.stock.alert.common.api.ApiMsgEnum;
import com.ljq.stock.alert.common.exception.CommonException;
import com.ljq.stock.alert.dao.UserInfoDao;
import com.ljq.stock.alert.model.entity.UserInfoEntity;
import com.ljq.stock.alert.model.param.user.*;
import com.ljq.stock.alert.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

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
		// 校验-邮箱/手机号是否已注册
		LambdaQueryWrapper<UserInfoEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(UserInfoEntity::getEmail, saveParam.getEmail())
				.or().eq(CharSequenceUtil.isNotBlank(saveParam.getMobilePhone()), UserInfoEntity::getMobilePhone,
				saveParam.getMobilePhone());
		UserInfoEntity userInfoDB = userInfoDao.selectOne(queryWrapper);
		if (Objects.nonNull(userInfoDB)) {
			if (Objects.equals(saveParam.getEmail(), userInfoDB.getEmail())) {
				throw new CommonException(ApiMsgEnum.USER_EMAIL_REGISTERED);
			}
			if (Objects.equals(saveParam.getMobilePhone(), userInfoDB.getMobilePhone())) {
				throw new CommonException(ApiMsgEnum.USER_MOBILE_PHONE_REGISTERED);
			}
		}
		// 请求参数获取
		UserInfoEntity userInfoParam = new UserInfoEntity();
		BeanUtil.copyProperties(saveParam, userInfoParam, CopyOptions.create().ignoreNullValue().ignoreError());
		// 生成账号
		userInfoParam.setAccount(IdUtil.objectId());
		// TODO 密码加密
		// 保存
		userInfoDao.insert(userInfoParam);
		return userInfoParam;
	}

	/**
	 * 用户注册
	 *
	 * @param registerParam
	 * @return
	 */
	@Override
	public UserInfoEntity register(UserRegisterParam registerParam) {
		UserInfoSaveParam saveParam = new UserInfoSaveParam();
		BeanUtil.copyProperties(registerParam, saveParam);
		UserInfoEntity userInfoDB = save(saveParam);
		// TODO 生成 Token
		return userInfoDB;
	}

	/**
	 * 用户登录
	 *
	 * @param loginParam
	 * @return
	 */
	@Override
	public UserInfoEntity login(UserLoginParam loginParam) {
		UserInfoEntity userInfoDB = userInfoDao.login(loginParam.getAccount());
		if (Objects.isNull(userInfoDB)) {
			throw new CommonException(ApiMsgEnum.USER_ACCOUNT_NOT_EXIST);
		}
		// TODO 密码校验
		if (!Objects.equals(loginParam.getPasscode(), userInfoDB.getPasscode())) {
			throw new CommonException(ApiMsgEnum.USER_PASSCODE_ERROR);
		}
		// TODO 过滤密码,返回Token

		return userInfoDB;
	}

	/**
	 * 查询详情(单条)
	 *
	 * @param infoParam
	 * @return
	 */
	@Override
	public UserInfoEntity info(UserInfoInfoParam infoParam) {
		return userInfoDao.selectById(infoParam.getId());
	}

	/**
	 * 分页查询
	 *
	 * @param listParam
	 * @return
	 */
	@Override
	public IPage<UserInfoEntity> page(UserInfoListParam listParam) {
		LambdaQueryWrapper<UserInfoEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(Objects.nonNull(listParam.getId()), UserInfoEntity::getId, listParam.getId())
				.like(CharSequenceUtil.isNotBlank(listParam.getNickName()), UserInfoEntity::getNickName,
						listParam.getNickName())
				.like(CharSequenceUtil.isNotBlank(listParam.getMobilePhone()), UserInfoEntity::getMobilePhone,
						listParam.getMobilePhone())
				.like(CharSequenceUtil.isNotBlank(listParam.getEmail()), UserInfoEntity::getEmail, listParam.getEmail());
		IPage<UserInfoEntity> page = new Page<>(listParam.getCurrentPage(), listParam.getPageSize());
		return userInfoDao.selectPage(page, queryWrapper);
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

		// 删除关注股票

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
