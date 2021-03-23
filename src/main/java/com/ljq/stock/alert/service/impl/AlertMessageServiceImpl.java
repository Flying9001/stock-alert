package com.ljq.stock.alert.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljq.stock.alert.dao.AlertMessageDao;
import com.ljq.stock.alert.model.entity.AlertMessageEntity;
import com.ljq.stock.alert.model.param.message.*;
import com.ljq.stock.alert.service.AlertMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 预警消息业务层具体实现类
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Service("alertMessageService")
@Transactional(rollbackFor = {Exception.class})
@Slf4j
public class AlertMessageServiceImpl implements AlertMessageService {

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
		// 请求参数获取
		AlertMessageEntity alertMessageParam = new AlertMessageEntity();
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
	public AlertMessageEntity info(AlertMessageInfoParam infoParam) {
		// 请求参数获取
		AlertMessageEntity alertMessageParam = new AlertMessageEntity();
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
	public IPage<AlertMessageEntity> page(AlertMessageListParam listParam) {
		// 请求参数获
		AlertMessageEntity alertMessageParam = new AlertMessageEntity();
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
	public void update(AlertMessageUpdateParam updateParam) {
		// 请求参数获取
		AlertMessageEntity alertMessageParam = new AlertMessageEntity();
		alertMessageParam.setId(updateParam.getId());

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
	public void delete(AlertMessageDeleteParam deleteParam) {
		// 请求参数获取
		AlertMessageEntity alertMessageParam = new AlertMessageEntity();
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
	public void deleteBatch(AlertMessageDeleteBatchParam deleteBatchParam) {
		// 请求参数获取
			AlertMessageEntity alertMessageParam = new AlertMessageEntity();
		// 判断对象是否存在

		// 更新对象

	}



}
