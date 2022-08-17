package com.ljq.stock.alert.web.controller.app;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljq.stock.alert.common.api.ApiMsgEnum;
import com.ljq.stock.alert.common.api.ApiResult;
import com.ljq.stock.alert.model.entity.UserStockEntity;
import com.ljq.stock.alert.model.param.userstock.*;
import com.ljq.stock.alert.service.UserStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户股票
 * 
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/user/stock")
@Api(value = "用户股票", tags = "用户股票")
public class UserStockController {

	@Autowired
	private UserStockService userStockService;

    /**
     * 新增(单条)
     *
     * @param saveParam
     * @return
     */
    @PostMapping(value = "/add", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "用户股票新增(单条)",  notes = "用户股票新增(单条)")
    public ResponseEntity<ApiResult<UserStockEntity>> save(@Validated @RequestBody UserStockSaveParam saveParam) {
        if (saveParam.getMaxPrice().compareTo(saveParam.getMinPrice()) < 0) {
            return ResponseEntity.ok(ApiResult.fail(ApiMsgEnum.USER_STOCK_MAX_PRICE_LESS_THAN_MIN_PRICE));
        }
        return ResponseEntity.ok(ApiResult.success(userStockService.save(saveParam)));
    }

    /**
     * 查询详情(单条)
     *
     * @param infoParam
     * @return
     */
    @GetMapping(value = "/info", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "用户股票查询详情(单条)",  notes = "用户股票查询详情(单条)")
    public ResponseEntity<ApiResult<UserStockEntity>> info(@Validated UserStockInfoParam infoParam) {
        return ResponseEntity.ok(ApiResult.success(userStockService.info(infoParam)));
    }

    /**
     * 分页查询
     *
     * @param listParam
     * @return
     */
    @GetMapping(value = "/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "用户股票分页查询",  notes = "用户股票分页查询")
    public ResponseEntity<ApiResult<IPage<UserStockEntity>>> page(@Validated UserStockListParam listParam) {
        return ResponseEntity.ok(ApiResult.success(userStockService.page(listParam)));
    }

    /**
     * 修改(单条)
     *
     * @param
     * @return
     */
    @PutMapping(value = "/update", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "用户股票修改(单条)",  notes = "用户股票修改(单条)")
    public ResponseEntity<ApiResult<Void>> update(@Validated @RequestBody UserStockUpdateParam updateParam) {
        userStockService.update(updateParam);
        return ResponseEntity.ok(ApiResult.success());
    }

    /**
     * 删除(单条)
     *
     * @param deleteParam
     * @return
     */
    @DeleteMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "用户股票删除(单条)",  notes = "用户股票删除(单条)")
    public ResponseEntity<ApiResult<Void>> delete(@Validated @RequestBody UserStockDeleteParam deleteParam) {
        userStockService.delete(deleteParam);
        return ResponseEntity.ok(ApiResult.success());
    }

    /**
     * 批量删除
     *
     * @param deleteBatchParam
     * @return
     */
    @DeleteMapping(value = "/delete/batch", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "用户股票批量删除",  notes = "用户股票批量删除")
    public ResponseEntity<ApiResult<Void>> deleteBatch(@Validated @RequestBody UserStockDeleteBatchParam deleteBatchParam) {
        userStockService.deleteBatch(deleteBatchParam);
        return ResponseEntity.ok(ApiResult.success());
    }




}
