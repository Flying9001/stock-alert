package com.ljq.stock.alert.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljq.stock.alert.common.api.ApiMsgEnum;
import com.ljq.stock.alert.common.api.ApiResult;
import com.ljq.stock.alert.common.component.WxPusherClient;
import com.ljq.stock.alert.common.constant.EnableEnum;
import com.ljq.stock.alert.common.constant.UserPushConst;
import com.ljq.stock.alert.dao.UserInfoDao;
import com.ljq.stock.alert.dao.UserPushTypeDao;
import com.ljq.stock.alert.model.entity.UserInfoEntity;
import com.ljq.stock.alert.model.entity.UserPushTypeEntity;
import com.ljq.stock.alert.model.param.common.WxPusherCallbackParam;
import com.ljq.stock.alert.model.param.userpushtype.*;
import com.ljq.stock.alert.model.vo.UserTokenVo;
import com.ljq.stock.alert.service.UserPushTypeService;
import com.ljq.stock.alert.service.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @Description: 用户消息推送方式服务实现
 * @Author: junqiang.lu
 * @Date: 2023/7/31
 */
@Slf4j
@Service
@Transactional(rollbackFor = {Exception.class})
public class UserPushTypeServiceImpl extends ServiceImpl<UserPushTypeDao, UserPushTypeEntity>
        implements UserPushTypeService {

    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private WxPusherClient wxPusherClient;

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
        List<UserPushTypeEntity> entityList = super.list(Wrappers.lambdaQuery(UserPushTypeEntity.class)
                .eq(UserPushTypeEntity::getUserId, userTokenVo.getId()));
        if (CollUtil.isNotEmpty(entityList) && entityList.size() >= UserPushConst.USER_PUSH_TYPE_MAX) {
            return ApiResult.fail(ApiMsgEnum.USER_PUSH_TYPE_MAX_ERROR);
        }
        // 校验是否重复插入
        for (UserPushTypeEntity entity : entityList) {
            if (Objects.equals(entity.getPushType(), userPushTypeSaveParam.getPushType())) {
                return ApiResult.fail(ApiMsgEnum.USER_PUSH_TYPE_REPEAT);
            }
        }
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
     * 添加 WxPusher 推送方式
     *
     * @param callbackParam
     * @return
     */
    @Override
    public ApiResult addWxPusher(WxPusherCallbackParam callbackParam) {
        Long userId = Long.parseLong(callbackParam.getData().getExtra());
        // 校验用户是否存在
        UserInfoEntity userInfoDb = userInfoDao.selectById(userId);
        if (Objects.isNull(userInfoDb)) {
            return ApiResult.fail(ApiMsgEnum.USER_ACCOUNT_NOT_EXIST);
        }
        // 校验推送方式数量
        List<UserPushTypeEntity> entityList = super.list(Wrappers.lambdaQuery(UserPushTypeEntity.class)
                .eq(UserPushTypeEntity::getUserId, userId));
        if (CollUtil.isNotEmpty(entityList) && entityList.size() >= UserPushConst.USER_PUSH_TYPE_MAX) {
            return ApiResult.fail(ApiMsgEnum.USER_PUSH_TYPE_MAX_ERROR);
        }
        // 校验是否重复插入
        for (UserPushTypeEntity entity : entityList) {
            if (Objects.equals(entity.getPushType(), UserPushConst.USER_PUSH_TYPE_WXPUSHER)) {
                return ApiResult.fail(ApiMsgEnum.USER_PUSH_TYPE_REPEAT);
            }
        }
        // 请求参数获取
        UserPushTypeEntity userPushTypeParam = new UserPushTypeEntity();
        userPushTypeParam.setUserId(userId);
        userPushTypeParam.setPushType(UserPushConst.USER_PUSH_TYPE_WXPUSHER);
        userPushTypeParam.setReceiveAddress(callbackParam.getData().getUid());
        userPushTypeParam.setEnable(EnableEnum.ENABLE.getCode());
        // 保存
        super.save(userPushTypeParam);
        return ApiResult.success();
    }

    /**
     * 创建 wxPusher 二维码
     *
     * @return
     */
    @Override
    public ApiResult createWxPusherQrCode() {
        UserTokenVo userTokenVo = SessionUtil.currentSession().getUserToken();
        WxPusherClient.CreateQrCodeParam createQrCodeParam = new WxPusherClient.CreateQrCodeParam();
        createQrCodeParam.setExtra(String.valueOf(userTokenVo.getId()));
        return ApiResult.success(wxPusherClient.createQrCode(createQrCodeParam));
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
        // 校验请求参数
        UserPushTypeEntity pushTypeDb = this.getUserPushType(userPushTypeUpdateParam.getId());
        if (Objects.isNull(pushTypeDb)) {
            return ApiResult.fail(ApiMsgEnum.USER_PUSH_TYPE_NOT_EXIST);
        }
        if (Objects.equals(UserPushConst.USER_PUSH_TYPE_SMS, pushTypeDb.getPushType())
                || Objects.equals(UserPushConst.USER_PUSH_TYPE_EMAIL, pushTypeDb.getPushType())) {
            return ApiResult.fail(ApiMsgEnum.USER_PUSH_TYPE_PRESET_NO_EDIT);
        }
        // 更新对象
        UserPushTypeEntity userPushTypeParam = new UserPushTypeEntity();
        BeanUtil.copyProperties(userPushTypeUpdateParam,userPushTypeParam,CopyOptions.create()
                .ignoreError().ignoreNullValue());
        super.updateById(userPushTypeParam);
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
        // 校验请求参数
        UserPushTypeEntity pushTypeDb = getUserPushType(userPushTypeDeleteParam.getId());
        if (Objects.isNull(pushTypeDb)) {
            return ApiResult.fail(ApiMsgEnum.USER_PUSH_TYPE_NOT_EXIST);
        }
        if (Objects.equals(UserPushConst.USER_PUSH_TYPE_SMS, pushTypeDb.getPushType())
                || Objects.equals(UserPushConst.USER_PUSH_TYPE_EMAIL, pushTypeDb.getPushType())) {
            return ApiResult.fail(ApiMsgEnum.USER_PUSH_TYPE_PRESET_NO_EDIT);
        }
        super.removeById(userPushTypeDeleteParam.getId());
        return ApiResult.success();
    }

    /**
     * 获取用户的推送方式
     *
     * @param id
     * @return
     */
    private UserPushTypeEntity getUserPushType(long id) {
        // 请求参数获取
        UserTokenVo userTokenVo = SessionUtil.currentSession().getUserToken();
        LambdaQueryWrapper<UserPushTypeEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserPushTypeEntity::getUserId, userTokenVo.getId())
                .eq(UserPushTypeEntity::getId, id);
        // 校验请求参数
        return super.getOne(queryWrapper);
    }




}
