package com.ljq.stock.alert.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ljq.stock.alert.model.entity.AlertMessageEntity;
import org.springframework.stereotype.Repository;

/**
 * 预警消息
 * 
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Repository
public interface AlertMessageDao extends BaseMapper<AlertMessageEntity> {

    /**
     * 校验重复性
     *
     * @param alertMessage
     * @return
     */
    int validateRepeat(AlertMessageEntity alertMessage);
	
}
