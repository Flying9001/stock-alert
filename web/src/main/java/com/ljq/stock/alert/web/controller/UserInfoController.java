package com.ljq.stock.alert.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ljq.stock.alert.common.api.ApiMsgEnum;
import com.ljq.stock.alert.common.api.ApiResult;
import com.ljq.stock.alert.common.component.RedisUtil;
import com.ljq.stock.alert.common.constant.CheckCodeTypeEnum;
import com.ljq.stock.alert.common.exception.CommonException;
import com.ljq.stock.alert.model.entity.UserInfoEntity;
import com.ljq.stock.alert.model.param.user.*;
import com.ljq.stock.alert.model.vo.UserTokenVo;
import com.ljq.stock.alert.service.UserInfoService;
import com.ljq.stock.alert.service.util.CheckCodeUtil;
import com.ljq.stock.alert.service.util.SessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

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
     * 修改密码
     *
     * @param updatePasscodeParam
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @PutMapping(value = "/update/passcode", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "修改密码",  notes = "修改密码")
    public ResponseEntity<ApiResult<Void>> updatePasscode(@Validated @RequestBody UserUpdatePasscodeParam
                                     updatePasscodeParam) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        // 验证码校验
        UserTokenVo tokenVo = SessionUtil.currentSession().getUserToken();
        boolean checkResult = CheckCodeUtil.validateCheckCodeValidity(updatePasscodeParam.getCheckCode(),
                CheckCodeUtil.generateCacheKey(tokenVo.getEmail(), CheckCodeTypeEnum.UPDATE_PASSCODE), redisUtil);
        if (!checkResult) {
            return ResponseEntity.ok(ApiResult.fail(ApiMsgEnum.CHECK_CODE_VALIDATE_ERROR));
        }
        return ResponseEntity.ok(userInfoService.updatePasscode(updatePasscodeParam));
    }

    /**
     * 修改邮箱
     *
     * @param updateEmailParam
     * @return
     * @throws JsonProcessingException
     */
    @PutMapping(value = "/update/email", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "修改邮箱",  notes = "修改邮箱")
    public ResponseEntity<ApiResult<String>> updateEmail(@Validated @RequestBody UserUpdateEmailParam updateEmailParam)
            throws JsonProcessingException {
        // 验证码校验
        boolean checkResult = CheckCodeUtil.validateCheckCodeValidity(updateEmailParam.getCheckCode(),
                CheckCodeUtil.generateCacheKey(updateEmailParam.getEmail(), CheckCodeTypeEnum.UPDATE_EMAIL), redisUtil);
        if (!checkResult) {
            return ResponseEntity.ok(ApiResult.fail(ApiMsgEnum.CHECK_CODE_VALIDATE_ERROR));
        }
        return ResponseEntity.ok(userInfoService.updateEmail(updateEmailParam));
    }


}
