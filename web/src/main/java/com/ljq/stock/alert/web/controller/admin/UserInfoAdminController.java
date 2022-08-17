package com.ljq.stock.alert.web.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljq.stock.alert.common.api.ApiResult;
import com.ljq.stock.alert.model.entity.UserInfoEntity;
import com.ljq.stock.alert.model.param.user.*;
import com.ljq.stock.alert.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 用户信息-后台管理控制层
 * @Author: junqiang.lu
 * @Date: 2022/5/16
 */
@RestController
@RequestMapping(value = "/admin/user/info")
@Api(value = "用户信息-后台管理", tags = "用户信息-后台管理")
public class UserInfoAdminController {

    @Autowired
    private UserInfoService userInfoService;

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
