package com.ljq.stock.alert.web.controller.admin;

import com.ljq.stock.alert.model.param.userstockgroup.UserStockGroupInfoParam;
import com.ljq.stock.alert.model.param.userstockgroup.UserStockGroupListAdminParam;
import com.ljq.stock.alert.service.UserStockGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 用户股票分组-后台管理控制层
 * @Author: junqiang.lu
 * @Date: 2022/8/17
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/user/stock/group")
@Api(value = "用户股票分组-后台管理", tags = "用户股票分组-后台管理")
public class UserStockGroupAdminController {

    @Autowired
    private UserStockGroupService userStockGroupService;

    /**
     * 查询详情(单条)
     *
     * @param userStockGroupInfoParam
     * @return
     */
    @GetMapping(value = "/query/one", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "用户股票分组查询详情(单条)",  notes = "用户股票分组查询详情(单条)")
    public ResponseEntity<?> info(@Validated UserStockGroupInfoParam userStockGroupInfoParam)  {
        return ResponseEntity.ok(userStockGroupService.infoAdmin(userStockGroupInfoParam));
    }

    /**
     * 查询列表
     *
     * @param listAdminParam
     * @return
     */
    @GetMapping(value = "/query/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "用户股票分组查询列表",  notes = "用户股票分组查询列表")
    public ResponseEntity<?> list(@Validated UserStockGroupListAdminParam listAdminParam)  {
        return ResponseEntity.ok(userStockGroupService.listAdmin(listAdminParam));
    }


}
