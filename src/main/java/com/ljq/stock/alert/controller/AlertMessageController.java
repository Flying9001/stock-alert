package com.ljq.stock.alert.controller;

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
     * 新增(单条)
     *
     * @param saveParam
     * @return
     */
    @PostMapping(value = "/add", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "预警消息新增(单条)",  notes = "预警消息新增(单条)")
    public ResponseEntity<ApiResult<AlertMessageEntity>> save(@Validated @RequestBody AlertMessageSaveParam saveParam) {
        return ResponseEntity.ok(ApiResult.success(alertMessageService.save(saveParam)));
    }

    /**
     * 查询详情(单条)
     *
     * @param infoParam
     * @return
     */
    @GetMapping(value = "/info", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "预警消息查询详情(单条)",  notes = "预警消息查询详情(单条)")
    public ResponseEntity<ApiResult<AlertMessageEntity>> info(@Validated AlertMessageInfoParam infoParam) {
        return ResponseEntity.ok(ApiResult.success(alertMessageService.info(infoParam)));
    }

    /**
     * 分页查询api
     *
     * @param listParam
     * @return
     */
    @GetMapping(value = "/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "预警消息分页查询",  notes = "预警消息分页查询")
    public ResponseEntity<ApiResult<IPage<AlertMessageEntity>>> page(@Validated AlertMessageListParam listParam) {
        return ResponseEntity.ok(ApiResult.success(alertMessageService.page(listParam)));
    }

    /**
     * 修改(单条)
     *
     * @param
     * @return
     */
    @PutMapping(value = "/update", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "预警消息修改(单条)",  notes = "预警消息修改(单条)")
    public ResponseEntity<ApiResult<Void>> update(@Validated @RequestBody AlertMessageUpdateParam updateParam) {
        alertMessageService.update(updateParam);
        return ResponseEntity.ok(ApiResult.success());
    }

    /**
     * 删除(单条)
     *
     * @param deleteParam
     * @return
     */
    @DeleteMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "预警消息删除(单条)",  notes = "预警消息删除(单条)")
    public ResponseEntity<ApiResult<Void>> delete(@Validated @RequestBody AlertMessageDeleteParam deleteParam) {
        alertMessageService.delete(deleteParam);
        return ResponseEntity.ok(ApiResult.success());
    }

    /**
     * 批量删除
     *
     * @param deleteBatchParam
     * @return
     */
    @DeleteMapping(value = "/delete/batch", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "预警消息批量删除",  notes = "预警消息批量删除")
    public ResponseEntity<ApiResult<Void>> deleteBatch(@Validated @RequestBody AlertMessageDeleteBatchParam deleteBatchParam) {
        alertMessageService.deleteBatch(deleteBatchParam);
        return ResponseEntity.ok(ApiResult.success());
    }




}
