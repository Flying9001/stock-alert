package com.ljq.stock.alert.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljq.stock.alert.model.entity.MessagePushResultEntity;
import org.springframework.stereotype.Repository;

/**
 * 消息推送结果
 * 
 * @author junqiang.lu
 * @date 2023-08-04 17:50:24
 */
@Repository
public interface MessagePushResultDao extends BaseMapper<MessagePushResultEntity> {
	
}
