package com.ljq.stock.alert.service;

import com.ljq.stock.alert.common.api.ApiResult;
import com.ljq.stock.alert.model.param.stockgroupstock.*;

/**
 * 用户股票分组关联股票业务层接口
 * 
 * @author junqiang.lu
 * @date 2021-12-21 11:21:28
 */
public interface StockGroupStockService {

	/**
     * 保存(单条)
     *
     * @param stockGroupStockSaveParam
     * @return
     */
	ApiResult save(StockGroupStockSaveParam stockGroupStockSaveParam);

	/**
     * 查询详情(单条)
     *
     * @param stockGroupStockInfoParam
     * @return
     */
	ApiResult info(StockGroupStockInfoParam stockGroupStockInfoParam);

	/**
     * 查询列表
     *
     * @param stockGroupStockListParam
     * @return
     */
	ApiResult list(StockGroupStockListParam stockGroupStockListParam);


	/**
     * 删除(单条)
     *
     * @param stockGroupStockDeleteParam
     * @return
     */
	ApiResult delete(StockGroupStockDeleteParam stockGroupStockDeleteParam);


}
