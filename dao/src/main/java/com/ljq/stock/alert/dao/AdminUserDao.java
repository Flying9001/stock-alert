package com.ljq.stock.alert.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljq.stock.alert.model.entity.AdminUserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 管理员用户
 * 
 * @author junqiang.lu
 * @date 2022-08-15 11:08:13
 */
@Repository
public interface AdminUserDao extends BaseMapper<AdminUserEntity> {

    /**
     * 登录
     *
     * @param account
     * @return
     */
    AdminUserEntity login(@Param("account") String account);
	
}
