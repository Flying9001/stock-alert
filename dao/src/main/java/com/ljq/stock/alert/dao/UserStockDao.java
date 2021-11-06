package com.ljq.stock.alert.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljq.stock.alert.model.entity.UserStockEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
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
     * 分页查询
     *
     * @param queryMap
     * @param page
     * @return
     */
    IPage<UserStockEntity> queryPage(@NotNull @Param("queryMap") Map<String, Object> queryMap,
                                     IPage<UserStockEntity> page);
	
}
