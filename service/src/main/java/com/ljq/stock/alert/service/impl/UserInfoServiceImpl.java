package com.ljq.stock.alert.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ljq.stock.alert.common.api.ApiMsgEnum;
import com.ljq.stock.alert.common.api.ApiResult;
import com.ljq.stock.alert.common.component.RedisUtil;
import com.ljq.stock.alert.common.config.WechatConfig;
import com.ljq.stock.alert.common.constant.*;
import com.ljq.stock.alert.common.exception.CommonException;
import com.ljq.stock.alert.common.util.Md5Util;
import com.ljq.stock.alert.dao.*;
import com.ljq.stock.alert.model.entity.*;
import com.ljq.stock.alert.model.param.user.*;
import com.ljq.stock.alert.model.vo.UserTokenVo;
import com.ljq.stock.alert.model.vo.WechatMiniLoginRespVo;
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
import java.util.HashMap;
import java.util.Map;
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
	private UserOauthDao userOauthDao;
	@Autowired
	private UserPushTypeDao userPushTypeDao;

	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private AlertMessageMqSender messageMqSender;

	@Autowired
	private WechatConfig wechatConfig;

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
		// 设置用户推送方式
		UserPushTypeEntity pushTypeEmail = new UserPushTypeEntity();
		pushTypeEmail.setUserId(userInfoDB.getId());
		pushTypeEmail.setPushType(UserPushConst.USER_PUSH_TYPE_EMAIL);
		pushTypeEmail.setReceiveAddress(userInfoDB.getEmail());
		pushTypeEmail.setEnable(EnableEnum.ENABLE.getCode());
		userPushTypeDao.insert(pushTypeEmail);
		if (StrUtil.isNotBlank(registerParam.getMobilePhone())) {
			UserPushTypeEntity pushTypePhone = new UserPushTypeEntity();
			pushTypePhone.setUserId(userInfoDB.getId());
			pushTypePhone.setPushType(UserPushConst.USER_PUSH_TYPE_SMS);
			pushTypePhone.setReceiveAddress(userInfoDB.getMobilePhone());
			pushTypePhone.setEnable(EnableEnum.DISABLE.getCode());
			userPushTypeDao.insert(pushTypePhone);
		}
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
	 * 用户微信小程序登录
	 *
	 * @param wechatMiniParam
	 * @return
	 */
	@Override
	public ApiResult loginByWechatMini(UserLoginByWechatMiniParam wechatMiniParam) {
		WechatMiniLoginRespVo loginRespVo = getWechatMiniAuth(wechatMiniParam.getCode());
		if (Objects.isNull(loginRespVo) || StrUtil.isBlank(loginRespVo.getOpenid())) {
			return ApiResult.fail(ApiMsgEnum.USER_LOGIN_WECHAT_MINI_ERROR);
		}
		// 判断是否绑定
		int count = userOauthDao.selectCount(Wrappers.lambdaQuery(UserOauthEntity.class)
				.eq(UserOauthEntity::getAccessId, loginRespVo.getOpenid()));
		if (count < 1) {
			return ApiResult.fail(ApiMsgEnum.USER_WECHAT_MINI_NOT_BIND);
		}
		UserInfoEntity userInfoDB = userInfoDao.loginByWechatMini(loginRespVo.getOpenid());
		// 过滤密码
		userInfoDB.setPasscode(null);
		// 生成 Token
		userInfoDB.setToken(TokenUtil.createToken(userInfoDB));
		return ApiResult.success(userInfoDB);
	}

	/**
	 * 用户绑定微信小程序
	 *
	 * @param bindWechatMiniParam
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public ApiResult bindWechatMini(UserBindWechatMiniParam bindWechatMiniParam) {
		WechatMiniLoginRespVo loginRespVo = getWechatMiniAuth(bindWechatMiniParam.getCode());
		if (Objects.isNull(loginRespVo) || StrUtil.isBlank(loginRespVo.getOpenid())) {
			return ApiResult.fail(ApiMsgEnum.USER_LOGIN_WECHAT_MINI_ERROR);
		}
		// 校验用户是否存在
		int countUser = userInfoDao.selectCount(Wrappers.lambdaQuery(UserInfoEntity.class)
				.eq(UserInfoEntity::getId, bindWechatMiniParam.getUserId()));
		if (countUser < 1) {
			return ApiResult.fail(ApiMsgEnum.USER_ACCOUNT_NOT_EXIST);
		}
		// 校验是否重复绑定
		int countUserOauth = userOauthDao.selectCount(Wrappers.lambdaQuery(UserOauthEntity.class)
				.eq(UserOauthEntity::getUserId, bindWechatMiniParam.getUserId()));
		if (countUserOauth > 0) {
			return ApiResult.fail(ApiMsgEnum.USER_WECHAT_MINI_BIND_REPEAT);
		}
		UserOauthEntity userOauthEntity = new UserOauthEntity();
		userOauthEntity.setUserId(bindWechatMiniParam.getUserId());
		userOauthEntity.setAccessId(loginRespVo.getOpenid());
		userOauthEntity.setLoginType(LoginTypeEnum.WECHAT_MINI.getType());
		userOauthEntity.setEnable(1);
		userOauthDao.insert(userOauthEntity);
		return ApiResult.success();
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
		page = userInfoDao.selectPage(page, queryWrapper);
		page.getRecords().stream().forEach(userInfo -> userInfo.setPasscode(null));
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
	public void update(UserInfoUpdateParam updateParam) {
		// 请求参数获取
		UserInfoEntity userInfoParam = new UserInfoEntity();
		BeanUtil.copyProperties(updateParam, userInfoParam, CopyOptions.create().ignoreError().ignoreNullValue());
		userInfoDao.updateById(userInfoParam);
		// 更新推送方式
		if (StrUtil.isNotBlank(updateParam.getMobilePhone())) {
			UserPushTypeEntity pushType = new UserPushTypeEntity();
			pushType.setReceiveAddress(updateParam.getMobilePhone());
			userPushTypeDao.update(pushType, Wrappers.lambdaUpdate(UserPushTypeEntity.class)
					.eq(UserPushTypeEntity::getUserId, updateParam.getId())
					.eq(UserPushTypeEntity::getPushType, UserPushConst.USER_PUSH_TYPE_SMS));
		}
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
	 */
	@Override
	public ApiResult updateEmail(UserUpdateEmailParam updateEmailParam){
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
		// 更新推送方式
		UserPushTypeEntity pushType = new UserPushTypeEntity();
		pushType.setReceiveAddress(updateEmailParam.getEmail());
		userPushTypeDao.update(pushType, Wrappers.lambdaUpdate(UserPushTypeEntity.class)
				.eq(UserPushTypeEntity::getUserId, tokenVo.getId())
				.eq(UserPushTypeEntity::getPushType, UserPushConst.USER_PUSH_TYPE_EMAIL));
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
		// 删除用户推送方式
		userPushTypeDao.delete(Wrappers.lambdaQuery(UserPushTypeEntity.class)
				.eq(UserPushTypeEntity::getUserId, deleteParam.getId()));
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

	/**
	 * 获取微信小程序认证信息
	 *
	 * @param code
	 * @return
	 */
	private WechatMiniLoginRespVo getWechatMiniAuth(String code) {
		Map<String, Object> paramMap = new HashMap<>(8);
		paramMap.put("appid", wechatConfig.getMiniAppId());
		paramMap.put("secret", wechatConfig.getMiniAppSecret());
		paramMap.put("js_code", code);
		paramMap.put("grant_type", "authorization_code");
		String wechatResponse = HttpUtil.get(wechatConfig.getMiniLoginUrl(), paramMap);
		log.debug("微信登录返回结果: {}", wechatResponse);
		return JSONUtil.toBean(wechatResponse, WechatMiniLoginRespVo.class);
	}



}
