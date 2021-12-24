package com.ljq.stock.alert.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljq.stock.alert.common.api.ApiMsgEnum;
import com.ljq.stock.alert.common.exception.CommonException;
import com.ljq.stock.alert.dao.AlertMessageDao;
import com.ljq.stock.alert.model.entity.AlertMessageEntity;
import com.ljq.stock.alert.model.param.message.*;
import com.ljq.stock.alert.model.vo.UserTokenVo;
import com.ljq.stock.alert.service.AlertMessageService;
import com.ljq.stock.alert.service.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 预警消息业务层具体实现类
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Service("alertMessageService")
@Transactional(rollbackFor = {Exception.class})
@Slf4j
public class AlertMessageServiceImpl extends ServiceImpl<AlertMessageDao, AlertMessageEntity>
		implements AlertMessageService {

	@Autowired
	private AlertMessageDao alertMessageDao;

	/**
	 * 保存(单条)
	 *
	 * @param saveParam
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public AlertMessageEntity save(AlertMessageSaveParam saveParam) {
		return new AlertMessageEntity();
	}

	/**
	 * 查询详情(单条)
	 *
	 * @param infoParam
	 * @return
	 */
	@Override
	public AlertMessageEntity info(AlertMessageInfoParam infoParam) {
		return alertMessageDao.selectOne(Wrappers.lambdaQuery(new AlertMessageEntity())
				.eq(AlertMessageEntity::getId, infoParam.getId())
				.eq(Objects.nonNull(infoParam.getUserId()), AlertMessageEntity::getUserId, infoParam.getUserId()));
	}

	/**
	 * 查询用户的消息详情
	 *
	 * @param infoUserParam
	 * @return
	 */
	@Override
	public AlertMessageEntity infoUser(AlertMessageInfoUserParam infoUserParam) {
		AlertMessageInfoParam infoParam = new AlertMessageInfoParam();
		infoParam.setId(infoUserParam.getId());
		UserTokenVo userTokenVo = SessionUtil.currentSession().getUserToken();
		infoParam.setUserId(userTokenVo.getId());
		return info(infoParam);
	}

	/**
	 * 分页查询
	 *
	 * @param listParam
	 * @return
	 */
	@Override
	public IPage<AlertMessageEntity> page(AlertMessageListParam listParam) {
		IPage<AlertMessageEntity> page = new Page<>(listParam.getCurrentPage(), listParam.getPageSize());
		// 请求参数获
		LambdaQueryWrapper<AlertMessageEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(Objects.nonNull(listParam.getUserId()), AlertMessageEntity::getUserId, listParam.getUserId())
				.eq(Objects.nonNull(listParam.getPhoneSend()), AlertMessageEntity::getPhoneSend, listParam.getPhoneSend())
				.eq(Objects.nonNull(listParam.getEmailSend()), AlertMessageEntity::getEmailSend, listParam.getEmailSend())
				.eq(Objects.nonNull(listParam.getStockId()), AlertMessageEntity::getStockId, listParam.getStockId())
				.like(CharSequenceUtil.isNotBlank(listParam.getContent()), AlertMessageEntity::getContent, listParam.getContent());
		// 分页查询
		page = alertMessageDao.selectPage(page, queryWrapper);
		return page;
	}

	/**
	 * 分页查询用户消息列表
	 *
	 * @param listUserParam
	 * @return
	 */
	@Override
	public IPage<AlertMessageEntity> pageUser(AlertMessageListUserParam listUserParam) {
		AlertMessageListParam listParam =  (AlertMessageListParam) listUserParam;
		UserTokenVo userTokenVo = SessionUtil.currentSession().getUserToken();
		listParam.setUserId(userTokenVo.getId());
		return page(listParam);
	}

	/**
	 * 删除(单条)
	 *
	 * @param deleteParam
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void delete(AlertMessageDeleteParam deleteParam) {
		LambdaQueryWrapper<AlertMessageEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(AlertMessageEntity::getId, deleteParam.getId())
				.eq(Objects.nonNull(deleteParam.getUserId()), AlertMessageEntity::getUserId, deleteParam.getUserId());
		// 判断对象是否存在
		AlertMessageEntity alertMessageDB = alertMessageDao.selectOne(queryWrapper);
		if (Objects.isNull(alertMessageDB)) {
			throw new CommonException(ApiMsgEnum.ALERT_MESSAGE_NOT_EXIST);
		}
		// 删除对象
		alertMessageDao.delete(queryWrapper);
	}

	/**
	 * 删除用户消息(单条)
	 *
	 * @param deleteUserParam
	 */
	@Override
	public void deleteUser(AlertMessageDeleteUserParam deleteUserParam) {
		AlertMessageDeleteParam deleteParam = new AlertMessageDeleteParam();
		deleteParam.setId(deleteUserParam.getId());
		UserTokenVo userTokenVo = SessionUtil.currentSession().getUserToken();
		deleteParam.setUserId(userTokenVo.getId());
		delete(deleteParam);
	}

	/**
	 * 批量删除
	 *
	 * @param deleteBatchParam
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void deleteBatch(AlertMessageDeleteBatchParam deleteBatchParam) {
		LambdaQueryWrapper<AlertMessageEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(Objects.nonNull(deleteBatchParam.getUserId()),AlertMessageEntity::getUserId,
				deleteBatchParam.getUserId())
				.in(AlertMessageEntity::getId, deleteBatchParam.getIdList());
		// 判断对象是否存在
		List<AlertMessageEntity> messageDBList = alertMessageDao.selectList(queryWrapper);
		if (CollectionUtil.isEmpty(messageDBList) || messageDBList.size() < deleteBatchParam.getIdList().size()) {
			throw new CommonException(ApiMsgEnum.ALERT_MESSAGE_NOT_EXIST);
		}
		// 删除对象
		alertMessageDao.delete(queryWrapper);
	}

	/**
	 * 批量删除用户消息
	 *
	 * @param deleteBatchUserParam
	 */
	@Override
	public void deleteBatchUser(AlertMessageDeleteBatchUserParam deleteBatchUserParam) {
		UserTokenVo userTokenVo = SessionUtil.currentSession().getUserToken();
		AlertMessageDeleteBatchParam deleteBatchParam = new AlertMessageDeleteBatchParam();
		deleteBatchParam.setUserId(userTokenVo.getId());
		deleteBatchParam.setIdList(deleteBatchUserParam.getIdList());
		deleteBatch(deleteBatchParam);
	}


}
