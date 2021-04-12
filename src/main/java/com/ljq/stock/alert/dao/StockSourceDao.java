package com.ljq.stock.alert.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljq.stock.alert.model.entity.StockSourceEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 股票源
 * 
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Repository
public interface StockSourceDao extends BaseMapper<StockSourceEntity> {

    /**
     * 分页查询用户关注的股票
     *
     * @param page
     * @param paramMap
     * @return
     */
    IPage<StockSourceEntity> queryPageByUser(IPage<StockSourceEntity> page,
                                             @Param("param") Map<String, Object> paramMap);
	
}
