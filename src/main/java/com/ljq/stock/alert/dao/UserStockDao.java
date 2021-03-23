package com.ljq.stock.alert.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljq.stock.alert.model.entity.UserStockEntity;
import org.springframework.stereotype.Repository;

/**
 * 用户股票
 * 
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Repository
public interface UserStockDao extends BaseMapper<UserStockEntity> {
	
}
