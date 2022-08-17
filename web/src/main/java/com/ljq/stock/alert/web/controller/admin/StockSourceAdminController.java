package com.ljq.stock.alert.web.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljq.stock.alert.common.api.ApiResult;
import com.ljq.stock.alert.model.entity.StockSourceEntity;
import com.ljq.stock.alert.model.param.stocksource.*;
import com.ljq.stock.alert.service.StockSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 股票源-后台管理控制层
 * @Author: junqiang.lu
 * @Date: 2022/8/16
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/stock/source")
@Api(value = "股票源-后台管理控制层", tags = "股票源-后台管理控制层")
public class StockSourceAdminController {

    @Autowired
    private StockSourceService stockSourceService;

    /**
     * 新增(单条)
     *
     * @param saveParam
     * @return
     */
    @PostMapping(value = "/add", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "股票源新增(单条)",  notes = "股票源新增(单条)")
    public ResponseEntity<ApiResult<StockSourceEntity>> save(@Validated @RequestBody StockSourceSaveParam saveParam) {
        return ResponseEntity.ok(ApiResult.success(stockSourceService.save(saveParam)));
    }

    /**
     * 查询详情(单条)
     *
     * @param infoParam
     * @return
     */
    @GetMapping(value = "/info", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "股票源查询详情(单条)",  notes = "股票源查询详情(单条)")
    public ResponseEntity<ApiResult<StockSourceEntity>> info(@Validated StockSourceInfoParam infoParam) {
        return ResponseEntity.ok(ApiResult.success(stockSourceService.info(infoParam)));
    }

    /**
     * 分页查询
     *
     * @param listParam
     * @return
     */
    @GetMapping(value = "/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "股票源分页查询",  notes = "股票源分页查询")
    public ResponseEntity<ApiResult<IPage<StockSourceEntity>>> page(@Validated StockSourceListParam listParam) {
        return ResponseEntity.ok(ApiResult.success(stockSourceService.page(listParam)));
    }

    /**
     * 删除(单条)
     *
     * @param deleteParam
     * @return
     */
    @DeleteMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "股票源删除(单条)",  notes = "股票源删除(单条)")
    public ResponseEntity<ApiResult<Void>> delete(@Validated @RequestBody StockSourceDeleteParam deleteParam) {
        stockSourceService.delete(deleteParam);
        return ResponseEntity.ok(ApiResult.success());
    }

    /**
     * 批量删除
     *
     * @param deleteBatchParam
     * @return
     */
    @DeleteMapping(value = "/delete/batch", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "股票源批量删除",  notes = "股票源批量删除")
    public ResponseEntity<ApiResult<Void>> deleteBatch(@Validated @RequestBody StockSourceDeleteBatchParam deleteBatchParam) {
        stockSourceService.deleteBatch(deleteBatchParam);
        return ResponseEntity.ok(ApiResult.success());
    }

    /**
     * 将所有数据库中的股票添加到缓存
     *
     * @return
     */
    @PutMapping(value = "/db/to/cache", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "将所有数据库中的股票添加到缓存",  notes = "将所有数据库中的股票添加到缓存")
    public ResponseEntity<ApiResult<Void>> allDbToCache() {
        return ResponseEntity.ok(stockSourceService.allDbToCache());
    }

    /**
     * 将缓存中的所有股票数据同步更新到数据库
     *
     * @return
     */
    @PutMapping(value = "/cache/to/db", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "将缓存中的所有股票数据同步更新到数据库",  notes = "将缓存中的所有股票数据同步更新到数据库")
    public ResponseEntity<ApiResult<Void>> allCacheToDb() {
        return ResponseEntity.ok(stockSourceService.allCacheToDb());
    }


}
