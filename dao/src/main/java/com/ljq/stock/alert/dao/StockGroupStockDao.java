package com.ljq.stock.alert.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljq.stock.alert.model.entity.StockGroupStockEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 用户股票分组关联股票
 * 
 * @author junqiang.lu
 * @date 2021-12-21 11:21:28
 */
@Repository
public interface StockGroupStockDao extends BaseMapper<StockGroupStockEntity> {

    /**
     * 查询用户股票分组关联股票详情
     *
     * @param id
     * @return
     */
    StockGroupStockEntity queryObject(@Param("id") Long id);

    /**
     * 分页查询
     *
     * @param queryMap
     * @param page
     * @return
     */
    IPage<StockGroupStockEntity> queryPage(@Param("queryMap") Map<String, Object> queryMap,
                                           IPage<StockGroupStockEntity> page);

    /**
     * 删除用户的所有分组内关联的股票
     *
     * @param userId
     * @return
     */
    int deleteByUser(@Param("userId") Long userId);
	
}
