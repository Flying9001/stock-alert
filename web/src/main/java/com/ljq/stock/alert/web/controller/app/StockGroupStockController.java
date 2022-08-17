package com.ljq.stock.alert.web.controller.app;

import com.ljq.stock.alert.model.param.stockgroupstock.StockGroupStockDeleteParam;
import com.ljq.stock.alert.model.param.stockgroupstock.StockGroupStockInfoParam;
import com.ljq.stock.alert.model.param.stockgroupstock.StockGroupStockListParam;
import com.ljq.stock.alert.model.param.stockgroupstock.StockGroupStockSaveParam;
import com.ljq.stock.alert.service.StockGroupStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户股票分组关联股票
 * 
 * @author junqiang.lu
 * @date 2021-12-21 11:21:28
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/stock/group/stock")
@Api(value = "用户股票分组关联股票控制层", tags = "用户股票分组关联股票控制层")
public class StockGroupStockController {

	@Autowired
	private StockGroupStockService stockGroupStockService;

    /**
     * 保存(单条)
     *
     * @param stockGroupStockSaveParam
     * @return
     */
    @PostMapping(value = "/add", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "用户股票分组关联股票保存(单条)",  notes = "用户股票分组关联股票保存(单条)")
    public ResponseEntity<?> save(@RequestBody StockGroupStockSaveParam stockGroupStockSaveParam) {
        return ResponseEntity.ok(stockGroupStockService.save(stockGroupStockSaveParam));
    }

    /**
     * 查询详情(单条)
     *
     * @param stockGroupStockInfoParam
     * @return
     */
    @GetMapping(value = "/query/one", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "用户股票分组关联股票查询详情(单条)",  notes = "用户股票分组关联股票查询详情(单条)")
    public ResponseEntity<?> info(@Validated StockGroupStockInfoParam stockGroupStockInfoParam)  {
        return ResponseEntity.ok(stockGroupStockService.info(stockGroupStockInfoParam));
    }

    /**
     * 查询列表
     *
     * @param stockGroupStockListParam
     * @return
     */
    @GetMapping(value = "/query/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "用户股票分组关联股票查询列表",  notes = "用户股票分组关联股票查询列表")
    public ResponseEntity<?> list(@Validated StockGroupStockListParam stockGroupStockListParam)  {
        return ResponseEntity.ok(stockGroupStockService.list(stockGroupStockListParam));
    }

    /**
     * 删除(单条)
     *
     * @param stockGroupStockDeleteParam
     * @return
     */
    @DeleteMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "用户股票分组关联股票删除(单条)",  notes = "用户股票分组关联股票删除(单条)")
    public ResponseEntity<?> delete(@RequestBody @Validated StockGroupStockDeleteParam stockGroupStockDeleteParam)  {
        return ResponseEntity.ok(stockGroupStockService.delete(stockGroupStockDeleteParam));
    }





}
