package com.ljq.stock.alert.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljq.stock.alert.common.api.ApiResult;
import com.ljq.stock.alert.dao.UserPushTypeDao;
import com.ljq.stock.alert.model.entity.UserPushTypeEntity;
import com.ljq.stock.alert.model.param.userpushtype.*;
import com.ljq.stock.alert.service.UserPushTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: 用户消息推送方式服务实现
 * @Author: junqiang.lu
 * @Date: 2023/7/31
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class UserPushTypeServiceImpl extends ServiceImpl<UserPushTypeDao, UserPushTypeEntity>
        implements UserPushTypeService {

    /**
     * 保存(单条)
     *
     * @param userPushTypeSaveParam
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public ApiResult save(UserPushTypeSaveParam userPushTypeSaveParam) {
        // 请求参数获取
        UserPushTypeEntity userPushTypeParam = new UserPushTypeEntity();
        BeanUtil.copyProperties(userPushTypeSaveParam,userPushTypeParam,CopyOptions.create()
                .setIgnoreNullValue(true).setIgnoreError(true));
        // 保存

        return ApiResult.success();
    }

    /**
     * 查询详情(单条)
     *
     * @param userPushTypeInfoParam
     * @return
     */
    @Override
    public ApiResult info(UserPushTypeInfoParam userPushTypeInfoParam) {
        // 请求参数获取
        UserPushTypeEntity userPushTypeParam = new UserPushTypeEntity();
        BeanUtil.copyProperties(userPushTypeInfoParam,userPushTypeParam,CopyOptions.create()
                .setIgnoreNullValue(true).setIgnoreError(true));
        // 查询

        return ApiResult.success();
    }

    /**
     * 查询列表
     *
     * @param userPushTypeListParam
     * @return
     */
    @Override
    public ApiResult list(UserPushTypeListParam userPushTypeListParam) {
        // 请求参数获取
        // 分页查询


        return ApiResult.success();
    }

    /**
     * 更新(单条)
     *
     * @param userPushTypeUpdateParam
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public ApiResult update(UserPushTypeUpdateParam userPushTypeUpdateParam) {
        // 请求参数获取
        UserPushTypeEntity userPushTypeParam = new UserPushTypeEntity();
        userPushTypeParam.setId(userPushTypeUpdateParam.getId());

        // 判断对象是否存在

        // 更新对象

        return ApiResult.success();
    }

    /**
     * 删除(单条)
     *
     * @param userPushTypeDeleteParam
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public ApiResult delete(UserPushTypeDeleteParam userPushTypeDeleteParam) {
        // 请求参数获取
        UserPushTypeEntity userPushTypeParam = new UserPushTypeEntity();
        BeanUtil.copyProperties(userPushTypeDeleteParam, userPushTypeParam, CopyOptions.create()
                .setIgnoreNullValue(true).setIgnoreError(true));
        // 判断对象是否存在

        // 更新对象

        return ApiResult.success();
    }




}
