package com.ljq.stock.alert.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljq.stock.alert.common.api.ApiMsgEnum;
import com.ljq.stock.alert.common.api.ApiResult;
import com.ljq.stock.alert.common.constant.EnableEnum;
import com.ljq.stock.alert.common.util.Md5Util;
import com.ljq.stock.alert.dao.AdminUserDao;
import com.ljq.stock.alert.model.entity.AdminUserEntity;
import com.ljq.stock.alert.model.param.adminuser.*;
import com.ljq.stock.alert.service.AdminUserService;
import com.ljq.stock.alert.service.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 管理员用户业务层具体实现类
 *
 * @author junqiang.lu
 * @date 2022-08-15 11:08:13
 */
@Slf4j
@Service("adminUserService")
public class AdminUserServiceImpl extends ServiceImpl<AdminUserDao, AdminUserEntity> implements AdminUserService {

	@Autowired
	private AdminUserDao adminUserDao;

	/**
	 * 保存(单条)
	 *
	 * @param adminUserSaveParam
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public ApiResult save(AdminUserSaveParam adminUserSaveParam){
		// 校验邮箱/手机号是否注册
		LambdaQueryWrapper<AdminUserEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(AdminUserEntity::getEmail, adminUserSaveParam.getEmail())
				.or().eq(StrUtil.isNotBlank(adminUserSaveParam.getMobilePhone()),
						AdminUserEntity::getMobilePhone, adminUserSaveParam.getMobilePhone());
		AdminUserEntity adminUserDB = adminUserDao.selectOne(queryWrapper);
		if (Objects.nonNull(adminUserDB)) {
			if (Objects.equals(adminUserSaveParam.getEmail(), adminUserDB.getEmail())) {
				return ApiResult.fail(ApiMsgEnum.USER_EMAIL_REGISTERED);
			}
			if (Objects.equals(adminUserSaveParam.getMobilePhone(), adminUserDB.getMobilePhone())) {
				return ApiResult.fail(ApiMsgEnum.USER_MOBILE_PHONE_REGISTERED);
			}
		}
		// 获取请求参数
		AdminUserEntity adminUserParam = new AdminUserEntity();
		BeanUtil.copyProperties(adminUserSaveParam, adminUserParam, CopyOptions.create()
				.ignoreNullValue().ignoreError());
		// 生成账号
		adminUserParam.setAccount(IdUtil.objectId());
		adminUserParam.setEnable(EnableEnum.ENABLE.getCode());
		// 密码加密
		try {
			adminUserParam.setPasscode(Md5Util.getEncryptedPwd(adminUserSaveParam.getPasscode()));
		} catch (Exception e) {
			log.error("密码加密失败", e);
			return ApiResult.fail(ApiMsgEnum.USER_PASSCODE_ENCRYPT_ERROR);
		}
		// 保存
		adminUserDao.insert(adminUserParam);
		// 过滤密码
		adminUserParam.setPasscode(null);
		return ApiResult.success(adminUserParam);
	}

	/**
	 * 登录
	 *
	 * @param loginParam
	 * @return
	 */
	@Override
	public ApiResult login(AdminUserLoginParam loginParam) {
		// 查询用户
		AdminUserEntity adminUserDB = adminUserDao.login(loginParam.getAccount());
		if (Objects.isNull(adminUserDB)) {
			return ApiResult.fail(ApiMsgEnum.USER_ACCOUNT_NOT_EXIST);
		}
		if (Objects.equals(adminUserDB.getEnable(), EnableEnum.DISABLE.getCode())) {
			return ApiResult.fail(ApiMsgEnum.ADMIN_USER_ACCOUNT_DISABLED);
		}
		// 密码校验
		boolean validateResult = false;
		try {
			validateResult = Md5Util.validPassword(loginParam.getPasscode(), adminUserDB.getPasscode());
		} catch (Exception e) {
			log.error("密码加密失败", e);
			return ApiResult.fail(ApiMsgEnum.USER_PASSCODE_ENCRYPT_ERROR);
		}
		if (!validateResult) {
			return ApiResult.fail(ApiMsgEnum.USER_PASSCODE_ERROR);
		}
		// 过滤密码
		adminUserDB.setPasscode(null);
		// 生成 Token
		adminUserDB.setToken(TokenUtil.createToken(adminUserDB));
		return ApiResult.success(adminUserDB);
	}

	/**
	 * 查询详情(单条)
	 *
	 * @param adminUserInfoParam
	 * @return
	 */
	@Override
	public ApiResult info(AdminUserInfoParam adminUserInfoParam){
		AdminUserEntity adminUserDB = adminUserDao.selectById(adminUserInfoParam.getId());
		// 过滤密码
		if (Objects.nonNull(adminUserDB)) {
			adminUserDB.setPasscode(null);
		}
		return ApiResult.success(adminUserDB);
	}

	/**
	 * 查询列表
	 *
	 * @param adminUserListParam
	 * @return
	 */
	@Override
	public ApiResult list(AdminUserListParam adminUserListParam){
		// 请求参数获取
		LambdaQueryWrapper<AdminUserEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(Objects.nonNull(adminUserListParam.getId()), AdminUserEntity::getId, adminUserListParam.getId())
				.like(StrUtil.isNotBlank(adminUserListParam.getNickName()), AdminUserEntity::getNickName,
						adminUserListParam.getNickName())
				.like(StrUtil.isNotBlank(adminUserListParam.getEmail()), AdminUserEntity::getEmail,
						adminUserListParam.getEmail())
				.like(StrUtil.isNotBlank(adminUserListParam.getMobilePhone()), AdminUserEntity::getMobilePhone,
						adminUserListParam.getMobilePhone());
		// 分页查询
		IPage<AdminUserEntity> page = new Page<>(adminUserListParam.getCurrentPage(), adminUserListParam.getPageSize());
		page = adminUserDao.selectPage(page, queryWrapper);
		// 过滤密码
		page.getRecords().stream().forEach(adminUser -> adminUser.setPasscode(null));
		return ApiResult.success(page);
	}

	/**
	 * 更新(单条)
	 *
	 * @param adminUserUpdateParam
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public ApiResult update(AdminUserUpdateParam adminUserUpdateParam){
		// 请求参数获取
		AdminUserEntity adminUserParam = new AdminUserEntity();
		BeanUtil.copyProperties(adminUserUpdateParam, adminUserParam, CopyOptions.create()
				.ignoreError().ignoreNullValue());
		int count = adminUserDao.updateById(adminUserParam);
		if (count < 1) {
			return ApiResult.fail(ApiMsgEnum.USER_ACCOUNT_NOT_EXIST);
		}
		return ApiResult.success();
	}

	/**
	 * 账号是否启用
	 *
	 * @param enableParam
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public ApiResult enable(AdminUserEnableParam enableParam) {
		// 请求参数获取
		AdminUserEntity adminUserParam = new AdminUserEntity();
		adminUserParam.setId(enableParam.getId());
		adminUserParam.setEnable(enableParam.getEnable());
		int count = adminUserDao.updateById(adminUserParam);
		if (count < 1) {
			return ApiResult.fail(ApiMsgEnum.USER_ACCOUNT_NOT_EXIST);
		}
		return ApiResult.success();
	}

	/**
	 * 删除(单条)
	 *
	 * @param adminUserDeleteParam
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public ApiResult delete(AdminUserDeleteParam adminUserDeleteParam){
		int count = adminUserDao.deleteById(adminUserDeleteParam.getId());
		if (count < 1) {
			return ApiResult.fail(ApiMsgEnum.USER_ACCOUNT_NOT_EXIST);
		}
		return ApiResult.success();
	}
	

	
}
