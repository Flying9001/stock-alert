package com.ljq.stock.alert.web.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljq.stock.alert.common.api.ApiResult;
import com.ljq.stock.alert.model.entity.AlertMessageEntity;
import com.ljq.stock.alert.model.param.message.AlertMessageDeleteBatchParam;
import com.ljq.stock.alert.model.param.message.AlertMessageDeleteParam;
import com.ljq.stock.alert.model.param.message.AlertMessageInfoParam;
import com.ljq.stock.alert.model.param.message.AlertMessageListParam;
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
 * @Description: 预警消息-后台管理控制层
 * @Author: junqiang.lu
 * @Date: 2022/8/17
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/message")
@Api(value = "预警消息-后台管理", tags = "预警消息-后台管理")
public class AlertMessageAdminController {

    @Autowired
    private AlertMessageService alertMessageService;

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
     * 分页查询
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
    public ResponseEntity<ApiResult<Void>> deleteBatch(@Validated @RequestBody AlertMessageDeleteBatchParam
                                                               deleteBatchParam) {
        alertMessageService.deleteBatch(deleteBatchParam);
        return ResponseEntity.ok(ApiResult.success());
    }




}
