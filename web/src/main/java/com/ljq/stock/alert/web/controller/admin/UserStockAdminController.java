package com.ljq.stock.alert.web.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ljq.stock.alert.common.api.ApiResult;
import com.ljq.stock.alert.model.entity.UserStockEntity;
import com.ljq.stock.alert.model.param.userstock.UserStockInfoParam;
import com.ljq.stock.alert.model.param.userstock.UserStockListAdminParam;
import com.ljq.stock.alert.service.UserStockService;
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
 * @Description: 用户关注股票-后台管理控制层
 * @Author: junqiang.lu
 * @Date: 2022/8/17
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/user/stock")
@Api(value = "用户关注股票-后台管理", tags = "用户关注股票-后台管理")
public class UserStockAdminController {

    @Autowired
    private UserStockService userStockService;

    /**
     * 查询详情(单条)
     *
     * @param infoParam
     * @return
     */
    @GetMapping(value = "/info", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "用户股票查询详情(单条)",  notes = "用户股票查询详情(单条)")
    public ResponseEntity<ApiResult<UserStockEntity>> info(@Validated UserStockInfoParam infoParam) {
        return ResponseEntity.ok(ApiResult.success(userStockService.infoAdmin(infoParam)));
    }

    /**
     * 分页查询
     *
     * @param listAdminParam
     * @return
     */
    @GetMapping(value = "/page", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "用户股票分页查询",  notes = "用户股票分页查询")
    public ResponseEntity<ApiResult<IPage<UserStockEntity>>> page(@Validated UserStockListAdminParam listAdminParam) {
        return ResponseEntity.ok(ApiResult.success(userStockService.pageAdmin(listAdminParam)));
    }


}
