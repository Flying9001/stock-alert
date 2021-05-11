package com.ljq.stock.alert.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljq.stock.alert.model.entity.UserStockEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 用户股票
 * 
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Repository
public interface UserStockDao extends BaseMapper<UserStockEntity> {

    /**
     * 查询列表
     *
     * @param queryMap
     * @param page
     * @return
     */
    IPage<UserStockEntity> queryList(@Param("queryMap") Map<String, Object> queryMap, IPage<UserStockEntity> page);
	
}
