package com.ljq.stock.alert.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ljq.stock.alert.common.api.ApiMsgEnum;
import com.ljq.stock.alert.common.api.ApiResult;
import com.ljq.stock.alert.common.component.RedisUtil;
import com.ljq.stock.alert.common.constant.CheckCodeTypeEnum;
import com.ljq.stock.alert.common.constant.UserConst;
import com.ljq.stock.alert.common.exception.CommonException;
import com.ljq.stock.alert.common.util.Md5Util;
import com.ljq.stock.alert.dao.*;
import com.ljq.stock.alert.model.entity.AlertMessageEntity;
import com.ljq.stock.alert.model.entity.UserInfoEntity;
import com.ljq.stock.alert.model.entity.UserStockEntity;
import com.ljq.stock.alert.model.entity.UserStockGroupEntity;
import com.ljq.stock.alert.model.param.user.*;
import com.ljq.stock.alert.model.vo.UserTokenVo;
import com.ljq.stock.alert.service.UserInfoService;
import com.ljq.stock.alert.service.component.AlertMessageMqSender;
import com.ljq.stock.alert.service.util.CheckCodeUtil;
import com.ljq.stock.alert.service.util.MessageHelper;
import com.ljq.stock.alert.service.util.SessionUtil;
import com.ljq.stock.alert.service.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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
	@Autowired
	private AlertMessageDao alertMessageDao;
	@Autowired
	private UserStockDao userStockDao;
	@Autowired
	private UserStockGroupDao userStockGroupDao;
	@Autowired
	private StockGroupStockDao stockGroupStockDao;

	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private AlertMessageMqSender messageMqSender;

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
		// 密码加密
		try {
			userInfoParam.setPasscode(Md5Util.getEncryptedPwd(userInfoParam.getPasscode()));
		} catch (Exception e) {
			log.error("密码加密失败", e);
			throw new CommonException(ApiMsgEnum.USER_PASSCODE_ENCRYPT_ERROR);
		}
		// 保存
		userInfoDao.insert(userInfoParam);
		// 过滤密码
		userInfoParam.setPasscode(null);
		return userInfoParam;
	}

	/**
	 * 获取验证码
	 *
	 * @param checkCodeParam
	 */
	@Override
	public void getCheckCode(UserGetCheckCodeParam checkCodeParam) {
		// 校验邮箱是否已注册
		boolean emailExist = validateExist(checkCodeParam.getEmail());
		//  防止验证码盗刷
		CheckCodeTypeEnum checkCodeType = CheckCodeTypeEnum.getType(checkCodeParam.getCheckCodeType());
		String redisKey = CheckCodeUtil.generateCacheKey(checkCodeParam.getEmail(), checkCodeType);
		boolean checkCodeSend = redisUtil.exists(redisKey);
		if (checkCodeSend) {
			return;
		}
		// 生成验证消息
		AlertMessageEntity message;
		String checkCode = CheckCodeUtil.generateCheckCode();
		switch (checkCodeType) {
			case REGISTER:
				if (emailExist) {
					throw new CommonException(ApiMsgEnum.USER_EMAIL_REGISTERED);
				}
				message = MessageHelper.createCheckMessage(checkCodeParam.getMobilePhone(), checkCodeParam.getEmail(),
						CheckCodeTypeEnum.REGISTER, checkCode);
				break;
			case SIGN_IN:
				if (!emailExist) {
					throw new CommonException(ApiMsgEnum.USER_ACCOUNT_NOT_EXIST);
				}
				message = MessageHelper.createCheckMessage(checkCodeParam.getMobilePhone(), checkCodeParam.getEmail(),
						CheckCodeTypeEnum.SIGN_IN, checkCode);
				break;
			case UPDATE_PASSCODE:
				if (!emailExist) {
					throw new CommonException(ApiMsgEnum.USER_ACCOUNT_NOT_EXIST);
				}
				message = MessageHelper.createCheckMessage(checkCodeParam.getMobilePhone(), checkCodeParam.getEmail(),
						CheckCodeTypeEnum.UPDATE_PASSCODE, checkCode);
				break;
			case UPDATE_EMAIL:
				if (emailExist) {
					throw new CommonException(ApiMsgEnum.USER_EMAIL_REGISTERED);
				}
				message = MessageHelper.createCheckMessage(checkCodeParam.getMobilePhone(), checkCodeParam.getEmail(),
						CheckCodeTypeEnum.UPDATE_EMAIL, checkCode);
				break;
			default:
				return;
		}
		// 发送验证码
		messageMqSender.sendUserOperate(message);
		// 缓存验证码
		redisUtil.set(redisKey, checkCode, UserConst.CHECK_CODE_EXPIRE_TIME_SECOND);
	}

	/**
	 * 用户注册
	 *
	 * @param registerParam
	 * @return
	 * @throws JsonProcessingException
	 */
	@Override
	public UserInfoEntity register(UserRegisterParam registerParam) throws JsonProcessingException {
		UserInfoSaveParam saveParam = new UserInfoSaveParam();
		BeanUtil.copyProperties(registerParam, saveParam);
		UserInfoEntity userInfoDB = save(saveParam);
		userInfoDB.setPasscode(null);
		// 生成 Token
		userInfoDB.setToken(TokenUtil.createToken(userInfoDB));
		return userInfoDB;
	}

	/**
	 * 用户登录
	 *
	 * @param loginParam
	 * @return
	 * @throws JsonProcessingException
	 */
	@Override
	public UserInfoEntity login(UserLoginParam loginParam) throws JsonProcessingException {
		UserInfoEntity userInfoDB = userInfoDao.login(loginParam.getAccount());
		if (Objects.isNull(userInfoDB)) {
			throw new CommonException(ApiMsgEnum.USER_ACCOUNT_NOT_EXIST);
		}
		// 密码校验
		boolean validateResult = false;
		try {
			validateResult = Md5Util.validPassword(loginParam.getPasscode(), userInfoDB.getPasscode());
		} catch (Exception e) {
			log.error("密码加密失败", e);
			throw new CommonException(ApiMsgEnum.USER_PASSCODE_ENCRYPT_ERROR);
		}
		if (!validateResult) {
			throw new CommonException(ApiMsgEnum.USER_PASSCODE_ERROR);
		}
		// 过滤密码
		userInfoDB.setPasscode(null);
		// 生成 Token
		userInfoDB.setToken(TokenUtil.createToken(userInfoDB));
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
		UserInfoEntity userInfoDB = userInfoDao.selectById(infoParam.getId());
		if (Objects.isNull(userInfoDB)) {
			throw new CommonException(ApiMsgEnum.USER_ACCOUNT_NOT_EXIST);
		}
		// 过滤密码
		userInfoDB.setPasscode(null);
		return userInfoDB;
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
		BeanUtil.copyProperties(updateParam, userInfoParam, CopyOptions.create().ignoreError().ignoreNullValue());
		userInfoDao.updateById(userInfoParam);
	}

	/**
	 * 修改密码
	 *
	 * @param updatePasscodeParam
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	@Override
	public ApiResult updatePasscode(UserUpdatePasscodeParam updatePasscodeParam) throws UnsupportedEncodingException,
			NoSuchAlgorithmException {
		UserTokenVo tokenVo = SessionUtil.currentSession().getUserToken();
		// 更新密码
		UserInfoEntity userInfo = new UserInfoEntity();
		userInfo.setId(tokenVo.getId());
		userInfo.setPasscode(Md5Util.getEncryptedPwd(updatePasscodeParam.getPasscode()));
		int count = userInfoDao.updateById(userInfo);
		if (count < 1) {
			return ApiResult.fail(ApiMsgEnum.USER_ACCOUNT_NOT_EXIST);
		}
		return ApiResult.success();
	}

	/**
	 * 修改邮箱
	 *
	 * @param updateEmailParam
	 * @return
	 * @throws JsonProcessingException
	 */
	@Override
	public ApiResult updateEmail(UserUpdateEmailParam updateEmailParam) throws JsonProcessingException {
		UserTokenVo tokenVo = SessionUtil.currentSession().getUserToken();
		// 更新邮箱
		UserInfoEntity userInfo = new UserInfoEntity();
		userInfo.setId(tokenVo.getId());
		userInfo.setEmail(updateEmailParam.getEmail());
		int count = userInfoDao.updateById(userInfo);
		if (count < 1) {
			return ApiResult.fail(ApiMsgEnum.USER_ACCOUNT_NOT_EXIST);
		}
		tokenVo.setEmail(updateEmailParam.getEmail());
		return ApiResult.success(TokenUtil.createToken(tokenVo));
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
		// 删除用户预警消息
		alertMessageDao.delete(Wrappers.lambdaQuery(AlertMessageEntity.class)
				.eq(AlertMessageEntity::getUserId,deleteParam.getId()));
		// 删除用户股票
		userStockDao.delete(Wrappers.lambdaQuery(UserStockEntity.class)
				.eq(UserStockEntity::getUserId, deleteParam.getId()));
		// 删除用户股票分组以及关联信息
		stockGroupStockDao.deleteByUser(deleteParam.getId());
		userStockGroupDao.delete(Wrappers.lambdaQuery(UserStockGroupEntity.class)
				.eq(UserStockGroupEntity::getUserId, deleteParam.getId()));
		// 删除用户信息
		userInfoDao.deleteById(deleteParam.getId());
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

	/**
	 * 校验账户是否存在
	 *
	 * @param account
	 * @return
	 */
	private boolean validateExist(String account) {
		UserInfoEntity userInfoDB = userInfoDao.login(account);
		if (Objects.isNull(userInfoDB)) {
			return false;
		}
		return true;
	}



}
