package com.ljq.stock.alert.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljq.stock.alert.model.entity.StockSourceEntity;
import org.springframework.stereotype.Repository;

/**
 * 股票源
 * 
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Repository
public interface StockSourceDao extends BaseMapper<StockSourceEntity> {
	
}
