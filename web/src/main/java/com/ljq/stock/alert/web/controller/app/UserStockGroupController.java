package com.ljq.stock.alert.web.controller.app;

import com.ljq.stock.alert.model.param.userstockgroup.*;
import com.ljq.stock.alert.service.UserStockGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户股票分组
 * 
 * @author junqiang.lu
 * @date 2021-12-21 11:21:27
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/user/stock/group")
@Api(value = "用户股票分组控制层", tags = "用户股票分组控制层")
public class UserStockGroupController {

	@Autowired
	private UserStockGroupService userStockGroupService;

    /**
     * 保存(单条)
     *
     * @param userStockGroupSaveParam
     * @return
     */
    @PostMapping(value = "/add", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "用户股票分组保存(单条)",  notes = "用户股票分组保存(单条)")
    public ResponseEntity<?> save(@RequestBody @Validated UserStockGroupSaveParam userStockGroupSaveParam) {
        return ResponseEntity.ok(userStockGroupService.save(userStockGroupSaveParam));
    }

    /**
     * 查询详情(单条)
     *
     * @param userStockGroupInfoParam
     * @return
     */
    @GetMapping(value = "/query/one", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "用户股票分组查询详情(单条)",  notes = "用户股票分组查询详情(单条)")
    public ResponseEntity<?> info(@Validated UserStockGroupInfoParam userStockGroupInfoParam)  {
        return ResponseEntity.ok(userStockGroupService.info(userStockGroupInfoParam));
    }

    /**
     * 查询列表
     *
     * @param userStockGroupListParam
     * @return
     */
    @GetMapping(value = "/query/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "用户股票分组查询列表",  notes = "用户股票分组查询列表")
    public ResponseEntity<?> list(@Validated UserStockGroupListParam userStockGroupListParam)  {
        return ResponseEntity.ok(userStockGroupService.list(userStockGroupListParam));
    }

    /**
     * 修改(单条)
     *
     * @param userStockGroupUpdateParam
     * @return
     */
    @PutMapping(value = "/update", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "用户股票分组修改(单条)",  notes = "用户股票分组修改(单条)")
    public ResponseEntity<?> update(@RequestBody @Validated UserStockGroupUpdateParam userStockGroupUpdateParam)  {
        return ResponseEntity.ok(userStockGroupService.update(userStockGroupUpdateParam));
    }

    /**
     * 删除(单条)
     *
     * @param userStockGroupDeleteParam
     * @return
     */
    @DeleteMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "用户股票分组删除(单条)",  notes = "用户股票分组删除(单条)")
    public ResponseEntity<?> delete(@RequestBody @Validated UserStockGroupDeleteParam userStockGroupDeleteParam)  {
        return ResponseEntity.ok(userStockGroupService.delete(userStockGroupDeleteParam));
    }





}
