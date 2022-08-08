package com.ljq.stock.alert.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljq.stock.alert.model.entity.UserOauthEntity;
import org.springframework.stereotype.Repository;

/**
 * @Description: 用户第三方登录数据持久层
 * @Author: junqiang.lu
 * @Date: 2022/8/5
 */
@Repository
public interface UserOauthDao extends BaseMapper<UserOauthEntity> {


}
