package com.ljq.stock.alert.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljq.stock.alert.common.api.ApiResult;
import com.ljq.stock.alert.model.entity.UserPushTypeEntity;
import com.ljq.stock.alert.model.param.userpushtype.*;

/**
 * @description 用户消息推送方式服务层
 * @author junqiang.lu
 * @date 2023-07-31
 */
public interface UserPushTypeService extends IService<UserPushTypeEntity> {

    /**
     * 保存(单条)
     *
     * @param userPushTypeSaveParam
     * @return
     */
    ApiResult save(UserPushTypeSaveParam userPushTypeSaveParam);

    /**
     * 查询详情(单条)
     *
     * @param userPushTypeInfoParam
     * @return
     */
    ApiResult info(UserPushTypeInfoParam userPushTypeInfoParam);

    /**
     * 查询列表
     *
     * @param userPushTypeListParam
     * @return
     */
    ApiResult list(UserPushTypeListParam userPushTypeListParam);

    /**
     * 更新(单条)
     *
     * @param userPushTypeUpdateParam
     * @return
     */
    ApiResult update(UserPushTypeUpdateParam userPushTypeUpdateParam);

    /**
     * 删除(单条)
     *
     * @param userPushTypeDeleteParam
     * @return
     */
    ApiResult delete(UserPushTypeDeleteParam userPushTypeDeleteParam);

}