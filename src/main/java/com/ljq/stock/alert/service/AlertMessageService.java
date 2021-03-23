package com.ljq.stock.alert.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljq.stock.alert.model.entity.AlertMessageEntity;
import com.ljq.stock.alert.model.param.message.*;

/**
 * 预警消息业务层接口
 * 
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
public interface AlertMessageService {

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
     * 分页查询
     *
     * @param listParam
     * @return
     */
	IPage<AlertMessageEntity> page(AlertMessageListParam listParam);

	/**
     * 更新(单条)
     *
     * @param updateParam
     * @return
     */
	void update(AlertMessageUpdateParam updateParam);

	/**
     * 删除(单条)
     *
     * @param deleteParam
     * @return
     */
	void delete(AlertMessageDeleteParam deleteParam);

	/**
     * 批量删除
     *
     * @param deleteBatchParam
     * @return
     */
	void deleteBatch(AlertMessageDeleteBatchParam deleteBatchParam);



}
