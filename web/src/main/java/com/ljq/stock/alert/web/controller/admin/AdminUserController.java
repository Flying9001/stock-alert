package com.ljq.stock.alert.web.controller.admin;

import com.ljq.stock.alert.common.api.ApiResult;
import com.ljq.stock.alert.model.param.adminuser.*;
import com.ljq.stock.alert.service.AdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员用户
 * 
 * @author junqiang.lu
 * @date 2022-08-15 11:08:13
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/admin/user")
@Api(value = "管理员用户控制层", tags = "管理员用户控制层")
public class AdminUserController {

	@Autowired
	private AdminUserService adminUserService;

    /**
     * 保存(单条)
     *
     * @param adminUserSaveParam
     * @return
     */
    @PostMapping(value = "/add", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "管理员用户保存(单条)",  notes = "管理员用户保存(单条)")
    public ResponseEntity<ApiResult> save(@RequestBody @Validated AdminUserSaveParam adminUserSaveParam){
        return ResponseEntity.ok(adminUserService.save(adminUserSaveParam));
    }

    /**
     * 登录
     *
     * @param loginParam
     * @return
     */
    @PostMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "管理员用户登录",  notes = "管理员用户登录")
    public ResponseEntity<ApiResult> login(@RequestBody @Validated AdminUserLoginParam loginParam){
        return ResponseEntity.ok(adminUserService.login(loginParam));
    }

    /**
     * 查询详情(单条)
     *
     * @param adminUserInfoParam
     * @return
     */
    @GetMapping(value = "/info", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "管理员用户查询详情(单条)",  notes = "管理员用户查询详情(单条)")
    public ResponseEntity<ApiResult> info(@Validated AdminUserInfoParam adminUserInfoParam) {
        return ResponseEntity.ok(adminUserService.info(adminUserInfoParam));
    }

    /**
     * 查询列表
     *
     * @param adminUserListParam
     * @return
     */
    @GetMapping(value = "/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "管理员用户查询列表",  notes = "管理员用户查询列表")
    public ResponseEntity<ApiResult> list(AdminUserListParam adminUserListParam) {
        return ResponseEntity.ok(adminUserService.list(adminUserListParam));
    }

    /**
     * 修改(单条)
     *
     * @param adminUserUpdateParam
     * @return
     */
    @PutMapping(value = "/update", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "管理员用户修改(单条)",  notes = "管理员用户修改(单条)")
    public ResponseEntity<ApiResult> update(@RequestBody @Validated AdminUserUpdateParam adminUserUpdateParam) {
        return ResponseEntity.ok(adminUserService.update(adminUserUpdateParam));
    }

    /**
     * 账号是否启用
     *
     * @param enableParam
     * @return
     */
    @PutMapping(value = "/enable", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "管理员用户账号是否启用",  notes = "管理员用户账号是否启用")
    public ResponseEntity<ApiResult> enable(@RequestBody @Validated AdminUserEnableParam enableParam) {
        return ResponseEntity.ok(adminUserService.enable(enableParam));
    }

    /**
     * 删除(单条)
     *
     * @param adminUserDeleteParam
     * @return
     */
    @DeleteMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "管理员用户删除(单条)",  notes = "管理员用户删除(单条)")
    public ResponseEntity<ApiResult> delete(@RequestBody @Validated AdminUserDeleteParam adminUserDeleteParam) {
        return ResponseEntity.ok(adminUserService.delete(adminUserDeleteParam));
    }





}
