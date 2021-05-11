package com.ljq.stock.alert.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljq.stock.alert.model.entity.AlertMessageEntity;
import com.ljq.stock.alert.model.param.message.*;

/**
 * 预警消息业务层接口
 * 
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
public interface AlertMessageService extends IService<AlertMessageEntity> {

	/**
     * 新增(单条)
     *
     * @param saveParam
     * @return
     */
	AlertMessageEntity save(AlertMessageSaveParam saveParam);

	/**
     * 查询详情(单条)
     *
     * @param infoParam
     * @return
     */
	AlertMessageEntity info(AlertMessageInfoParam infoParam);

	/**
	 * 查询用户的消息详情
	 *
	 * @param infoUserParam
	 * @return
	 */
	AlertMessageEntity infoUser(AlertMessageInfoUserParam infoUserParam);

	/**
     * 分页查询
     *
     * @param listParam
     * @return
     */
	IPage<AlertMessageEntity> page(AlertMessageListParam listParam);

	/**
	 * 分页查询用户消息列表
	 *
	 * @param listUserParam
	 * @return
	 */
	IPage<AlertMessageEntity> pageUser(AlertMessageListUserParam listUserParam);

	/**
     * 删除(单条)
     *
     * @param deleteParam
     * @return
     */
	void delete(AlertMessageDeleteParam deleteParam);

	/**
	 * 删除用户消息(单条)
	 *
	 * @param deleteUserParam
	 */
	void deleteUser(AlertMessageDeleteUserParam deleteUserParam);

	/**
     * 批量删除
     *
     * @param deleteBatchParam
     * @return
     */
	void deleteBatch(AlertMessageDeleteBatchParam deleteBatchParam);

	/**
	 * 批量删除用户消息
	 *
	 * @param deleteBatchUserParam
	 */
	void deleteBatchUser(AlertMessageDeleteBatchUserParam deleteBatchUserParam);



}
