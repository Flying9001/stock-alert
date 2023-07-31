package com.ljq.stock.alert.web.controller.app;

import com.ljq.stock.alert.model.param.userpushtype.*;
import com.ljq.stock.alert.service.UserPushTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 用户消息推送方式控制层
 * @Author: junqiang.lu
 * @Date: 2023/7/31
 */
@RestController
@RequestMapping(value = "/api/user/push/type")
@Api(value = "用户消息推送方式控制层", tags = "用户消息推送方式控制层")
public class UserPushTypeController {


    @Autowired
    private UserPushTypeService userPushTypeService;

    /**
     * 保存(单条)
     *
     * @param userPushTypeSaveParam
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = {"application/json"})
    @ApiOperation(value = "用户消息推送方式保存(单条)",  notes = "用户消息推送方式保存(单条)")
    @ResponseBody
    public ResponseEntity<?> save(@RequestBody UserPushTypeSaveParam userPushTypeSaveParam) throws Exception{
        return ResponseEntity.ok(userPushTypeService.save(userPushTypeSaveParam));
    }

    /**
     * 查询详情(单条)
     *
     * @param userPushTypeInfoParam
     * @return
     */
    @RequestMapping(value = "/query/one", method = RequestMethod.GET, produces = {"application/json"})
    @ApiOperation(value = "用户消息推送方式查询详情(单条)",  notes = "用户消息推送方式查询详情(单条)")
    @ResponseBody
    public ResponseEntity<?> info(UserPushTypeInfoParam userPushTypeInfoParam) {
        return ResponseEntity.ok(userPushTypeService.info(userPushTypeInfoParam));
    }

    /**
     * 查询列表
     *
     * @param userPushTypeListParam
     * @return
     */
    @RequestMapping(value = "/query/page", method = RequestMethod.GET, produces = {"application/json"})
    @ApiOperation(value = "用户消息推送方式查询列表",  notes = "用户消息推送方式查询列表")
    @ResponseBody
    public ResponseEntity<?> list(UserPushTypeListParam userPushTypeListParam) {
        return ResponseEntity.ok(userPushTypeService.list(userPushTypeListParam));
    }

    /**
     * 修改(单条)
     *
     * @param userPushTypeUpdateParam
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = {"application/json"})
    @ApiOperation(value = "用户消息推送方式修改(单条)",  notes = "用户消息推送方式修改(单条)")
    @ResponseBody
    public ResponseEntity<?> update(@RequestBody UserPushTypeUpdateParam userPushTypeUpdateParam) {
        return ResponseEntity.ok(userPushTypeService.update(userPushTypeUpdateParam));
    }

    /**
     * 删除(单条)
     *
     * @param userPushTypeDeleteParam
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = {"application/json"})
    @ApiOperation(value = "用户消息推送方式删除(单条)",  notes = "用户消息推送方式删除(单条)")
    @ResponseBody
    public ResponseEntity<?> delete(UserPushTypeDeleteParam userPushTypeDeleteParam) {
        return ResponseEntity.ok(userPushTypeService.delete(userPushTypeDeleteParam));
    }


}
