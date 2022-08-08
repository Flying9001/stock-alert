package com.ljq.stock.alert.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    /**
     * 微信小程序登录
     *
     * @param accessId
     * @return
     */
    UserInfoEntity loginByWechatMini(@Param("accessId") String accessId);

    /**
     * 统计拥有关注股票的用户数量
     *
     * @return
     */
    int queryCountWithStock();

    /**
     * 分页查询拥有关注股票的用户列表
     *
     * @param page
     * @return
     */
    Page<UserInfoEntity> queryPageWithStock(IPage<UserInfoEntity> page);
	
}
