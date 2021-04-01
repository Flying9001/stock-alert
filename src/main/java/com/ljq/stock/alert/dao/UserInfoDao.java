package com.ljq.stock.alert.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljq.stock.alert.model.entity.UserInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户信息
 * 
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Repository
public interface UserInfoDao extends BaseMapper<UserInfoEntity> {

    /**
     * 登录
     *
     * @param account
     * @return
     */
    UserInfoEntity login(@Param("account") String account);
	
}
