package com.ljq.stock.alert.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ljq.stock.alert.common.api.ApiMsgEnum;
import com.ljq.stock.alert.common.api.ApiResult;
import com.ljq.stock.alert.common.component.RedisUtil;
import com.ljq.stock.alert.common.constant.CheckCodeTypeEnum;
import com.ljq.stock.alert.common.exception.CommonException;
import com.ljq.stock.alert.model.entity.UserInfoEntity;
import com.ljq.stock.alert.model.param.user.*;
import com.ljq.stock.alert.service.UserInfoService;
import com.ljq.stock.alert.service.util.CheckCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户信息
 * 
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/user/info")
@Api(value = "用户信息", tags = "用户信息")
public class UserInfoController {

	@Autowired
	private UserInfoService userInfoService;
	@Autowired
    private RedisUtil redisUtil;

    /**
     * 新增(单条)
     *
     * @param saveParam
     * @return
     */
    @PostMapping(value = "/add", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "用户信息新增(单条)",  notes = "用户信息新增(单条)")
    public ResponseEntity<ApiResult<UserInfoEntity>> save(@Validated @RequestBody UserInfoSaveParam saveParam) {
        return ResponseEntity.ok(ApiResult.success(userInfoService.save(saveParam)));
    }

    /**
     * 获取验证码
     *
     * @param getCheckCodeParam
     * @return
     */
    @PostMapping(value = "/checkCode", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "获取验证码",  notes = "获取验证码")
    public ResponseEntity<ApiResult<Void>> getCheckCode(@Validated @RequestBody UserGetCheckCodeParam getCheckCodeParam) {
        userInfoService.getCheckCode(getCheckCodeParam);
        return ResponseEntity.ok(ApiResult.success());
    }

    /**
     * 用户注册
     *
     * @param registerParam
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping(value = "/register", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "用户注册",  notes = "用户注册")
    public ResponseEntity<ApiResult<UserInfoEntity>> register(@Validated @RequestBody UserRegisterParam registerParam)
            throws JsonProcessingException {
        // 验证码校验
        boolean checkResult = CheckCodeUtil.validateCheckCodeValidity(registerParam.getCheckCode(),
                CheckCodeUtil.generateCacheKey(registerParam.getEmail(), CheckCodeTypeEnum.REGISTER), redisUtil);
        if (!checkResult) {
            throw new CommonException(ApiMsgEnum.CHECK_CODE_VALIDATE_ERROR);
        }
        return ResponseEntity.ok(ApiResult.success(userInfoService.register(registerParam)));
    }

    /**
     * 用户登录
     *
     * @param loginParam
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "用户登录",  notes = "用户登录")
    public ResponseEntity<ApiResult<UserInfoEntity>> login(@Validated @RequestBody UserLoginParam loginParam)
            throws JsonProcessingException {
        return ResponseEntity.ok(ApiResult.success(userInfoService.login(loginParam)));
    }

    /**
     * 查询详情(单条)
     *
     * @param infoParam
     * @return
     */
    @GetMapping(value = "/info", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "用户信息查询详情(单条)",  notes = "用户信息查询详情(单条)")
    public ResponseEntity<ApiResult<UserInfoEntity>> info(@Validated UserInfoInfoParam infoParam) {
        return ResponseEntity.ok(ApiResult.success(userInfoService.info(infoParam)));
    }

    /**
     * 分页查询
     *
     * @param listParam
     * @return
     */
    @GetMapping(value = "/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "用户信息分页查询",  notes = "用户信息分页查询")
    public ResponseEntity<ApiResult<IPage<UserInfoEntity>>> page(@Validated UserInfoListParam listParam) {
        return ResponseEntity.ok(ApiResult.success(userInfoService.page(listParam)));
    }

    /**
     * 修改(单条)
     *
     * @param
     * @return
     */
    @PutMapping(value = "/update", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "用户信息修改(单条)",  notes = "用户信息修改(单条)")
    public ResponseEntity<ApiResult<Void>> update(@Validated @RequestBody UserInfoUpdateParam updateParam) {
        userInfoService.update(updateParam);
        return ResponseEntity.ok(ApiResult.success());
    }

    /**
     * 删除(单条)
     *
     * @param deleteParam
     * @return
     */
    @DeleteMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "用户信息删除(单条)",  notes = "用户信息删除(单条)")
    public ResponseEntity<ApiResult<Void>> delete(@Validated @RequestBody UserInfoDeleteParam deleteParam) {
        userInfoService.delete(deleteParam);
        return ResponseEntity.ok(ApiResult.success());
    }

    /**
     * 批量删除
     *
     * @param deleteBatchParam
     * @return
     */
    @DeleteMapping(value = "/delete/batch", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "用户信息批量删除",  notes = "用户信息批量删除")
    public ResponseEntity<ApiResult<Void>> deleteBatch(@Validated @RequestBody UserInfoDeleteBatchParam deleteBatchParam) {
        userInfoService.deleteBatch(deleteBatchParam);
        return ResponseEntity.ok(ApiResult.success());
    }




}
