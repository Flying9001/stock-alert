package com.ljq.stock.alert.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljq.stock.alert.common.api.ApiMsgEnum;
import com.ljq.stock.alert.common.api.ApiResult;
import com.ljq.stock.alert.common.constant.EnableEnum;
import com.ljq.stock.alert.common.constant.UserPushConst;
import com.ljq.stock.alert.dao.UserInfoDao;
import com.ljq.stock.alert.dao.UserPushTypeDao;
import com.ljq.stock.alert.model.entity.UserPushTypeEntity;
import com.ljq.stock.alert.model.param.userpushtype.*;
import com.ljq.stock.alert.model.vo.UserTokenVo;
import com.ljq.stock.alert.service.UserPushTypeService;
import com.ljq.stock.alert.service.util.SessionUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Description: 用户消息推送方式服务实现
 * @Author: junqiang.lu
 * @Date: 2023/7/31
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class UserPushTypeServiceImpl extends ServiceImpl<UserPushTypeDao, UserPushTypeEntity>
        implements UserPushTypeService {

    @Resource
    private UserInfoDao userInfoDao;

    /**
     * 保存(单条)
     *
     * @param userPushTypeSaveParam
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public ApiResult save(UserPushTypeSaveParam userPushTypeSaveParam) {
        UserTokenVo userTokenVo = SessionUtil.currentSession().getUserToken();
        // 校验推送方式数量
        int count = super.count(Wrappers.lambdaQuery(UserPushTypeEntity.class)
                .eq(UserPushTypeEntity::getUserId, userTokenVo.getId())
                .eq(UserPushTypeEntity::getEnable, EnableEnum.ENABLE.getCode()));
        if (count >= UserPushConst.USER_PUSH_TYPE_MAX) {
            return ApiResult.fail(ApiMsgEnum.USER_PUSH_TYPE_MAX_ERROR);
        }
        // TODO 校验是否重复插入

        // 请求参数获取
        UserPushTypeEntity userPushTypeParam = new UserPushTypeEntity();
        BeanUtil.copyProperties(userPushTypeSaveParam,userPushTypeParam,CopyOptions.create()
                .ignoreError().ignoreNullValue());
        userPushTypeParam.setUserId(userTokenVo.getId());
        // 保存
        super.save(userPushTypeParam);
        return ApiResult.success(userPushTypeParam);
    }

    /**
     * 查询详情(单条)
     *
     * @param userPushTypeInfoParam
     * @return
     */
    @Override
    public ApiResult info(UserPushTypeInfoParam userPushTypeInfoParam) {
        UserTokenVo userTokenVo = SessionUtil.currentSession().getUserToken();
        UserPushTypeEntity userPushTypeDb = super.getOne(Wrappers.lambdaQuery(UserPushTypeEntity.class)
                .eq(UserPushTypeEntity::getId, userPushTypeInfoParam.getId())
                .eq(UserPushTypeEntity::getUserId, userTokenVo.getId()));
        return ApiResult.success(userPushTypeDb);
    }

    /**
     * 查询列表
     *
     * @param userPushTypeListParam
     * @return
     */
    @Override
    public ApiResult list(UserPushTypeListParam userPushTypeListParam) {
        UserTokenVo userTokenVo = SessionUtil.currentSession().getUserToken();
        LambdaQueryWrapper<UserPushTypeEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserPushTypeEntity::getUserId, userTokenVo.getId())
                .eq(Objects.nonNull(userPushTypeListParam.getId()), UserPushTypeEntity::getId,
                        userPushTypeListParam.getId())
                .eq(Objects.nonNull(userPushTypeListParam.getEnable()), UserPushTypeEntity::getEnable,
                        userPushTypeListParam.getEnable());
        IPage<UserPushTypeEntity> page = super.page(new Page<>(userPushTypeListParam.getCurrentPage(),
                userPushTypeListParam.getPageSize()), queryWrapper);
        return ApiResult.success(page);
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
        UserTokenVo userTokenVo = SessionUtil.currentSession().getUserToken();
        // 请求参数获取
        UserPushTypeEntity userPushTypeParam = new UserPushTypeEntity();
        BeanUtil.copyProperties(userPushTypeUpdateParam,userPushTypeParam,CopyOptions.create()
                .ignoreError().ignoreNullValue());
        userPushTypeParam.setUserId(userTokenVo.getId());
        // 更新对象
        LambdaQueryWrapper<UserPushTypeEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserPushTypeEntity::getUserId, userTokenVo.getId())
                .eq(UserPushTypeEntity::getId, userPushTypeUpdateParam.getId());
        boolean updateFlag = super.update(userPushTypeParam, queryWrapper);
        if (!updateFlag) {
            return ApiResult.fail(ApiMsgEnum.USER_PUSH_TYPE_NOT_EXIST);
        }
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
        UserTokenVo userTokenVo = SessionUtil.currentSession().getUserToken();
        // 更新对象
        LambdaQueryWrapper<UserPushTypeEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserPushTypeEntity::getUserId, userTokenVo.getId())
                .eq(UserPushTypeEntity::getId, userPushTypeDeleteParam.getId());
        boolean updateFlag = super.remove(queryWrapper);
        if (!updateFlag) {
            return ApiResult.fail(ApiMsgEnum.USER_PUSH_TYPE_NOT_EXIST);
        }
        return ApiResult.success();
    }




}
