package com.ljq.stock.alert.web.controller.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljq.stock.alert.common.api.ApiResult;
import com.ljq.stock.alert.model.entity.AlertMessageEntity;
import com.ljq.stock.alert.model.param.message.*;
import com.ljq.stock.alert.service.AlertMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 预警消息
 * 
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/message")
@Api(value = "预警消息", tags = "预警消息")
public class AlertMessageController {

	@Autowired
	private AlertMessageService alertMessageService;

    /**
     * 查询用户的消息详情
     *
     * @param infoUserParam
     * @return
     */
    @GetMapping(value = "/info/user", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "查询用户的消息详情",  notes = "查询用户的消息详情")
    public ResponseEntity<ApiResult<AlertMessageEntity>> infoUser(@Validated AlertMessageInfoUserParam infoUserParam) {
        return ResponseEntity.ok(ApiResult.success(alertMessageService.infoUser(infoUserParam)));
    }

    /**
     * 分页查询用户的消息列表
     *
     * @param listUserParam
     * @return
     */
    @GetMapping(value = "/page/user", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "分页查询用户的消息列表",  notes = "分页查询用户的消息列表")
    public ResponseEntity<ApiResult<IPage<AlertMessageEntity>>> pageUser(@Validated AlertMessageListUserParam
                                                                                     listUserParam) {
        return ResponseEntity.ok(ApiResult.success(alertMessageService.pageUser(listUserParam)));
    }

    /**
     * 删除用户消息(单条)
     *
     * @param deleteUserParam
     * @return
     */
    @DeleteMapping(value = "/delete/user", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "删除用户消息(单条)",  notes = "删除用户消息(单条)")
    public ResponseEntity<ApiResult<Void>> deleteUser(@Validated @RequestBody AlertMessageDeleteUserParam
                                                                  deleteUserParam) {
        alertMessageService.deleteUser(deleteUserParam);
        return ResponseEntity.ok(ApiResult.success());
    }

    /**
     * 批量删除用户消息
     *
     * @param deleteBatchUserParam
     * @return
     */
    @DeleteMapping(value = "/delete/batch/user", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "批量删除用户消息",  notes = "批量删除用户消息")
    public ResponseEntity<ApiResult<Void>> deleteBatchUser(@Validated @RequestBody AlertMessageDeleteBatchUserParam
                                                                       deleteBatchUserParam) {
        alertMessageService.deleteBatchUser(deleteBatchUserParam);
        return ResponseEntity.ok(ApiResult.success());
    }




}
