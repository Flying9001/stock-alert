package com.ljq.stock.alert.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljq.stock.alert.model.entity.StockSourceEntity;
import com.ljq.stock.alert.model.param.stocksource.*;

/**
 * 股票源业务层接口
 * 
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
public interface StockSourceService extends IService<StockSourceEntity> {

	/**
	 * 新增(单条)
	 *
	 * @param saveParam
	 * @return
	 */
	StockSourceEntity save(StockSourceSaveParam saveParam);

	/**
     * 查询详情(单条)
     *
     * @param infoParam
     * @return
     */
	StockSourceEntity info(StockSourceInfoParam infoParam);

	/**
	 * 查询某一支股票实时数据
	 *
	 * @param infoRealTimeParam
	 * @return
	 */
	StockSourceEntity infoRealTime(StockSourceInfoRealTimeParam infoRealTimeParam);

	/**
     * 分页查询
     *
     * @param listParam
     * @return
     */
	IPage<StockSourceEntity> page(StockSourceListParam listParam);

	/**
	 * 分页查询实时股票数据
	 *
	 * @param listRealTimeParam
	 * @return
	 */
	IPage<StockSourceEntity> pageRealTime(StockSourceListRealTimeParam listRealTimeParam);

	/**
     * 删除(单条)
     *
     * @param deleteParam
     * @return
     */
	void delete(StockSourceDeleteParam deleteParam);

	/**
     * 批量删除
     *
     * @param deleteBatchParam
     * @return
     */
	void deleteBatch(StockSourceDeleteBatchParam deleteBatchParam);



}
