package com.ljq.stock.alert.web.controller.app;

import com.ljq.stock.alert.common.api.ApiResult;
import com.ljq.stock.alert.common.config.WechatConfig;
import com.ljq.stock.alert.common.config.WxPusherConfig;
import com.ljq.stock.alert.model.param.common.WechatMiniMsgParam;
import com.ljq.stock.alert.model.param.common.WxPusherCallbackParam;
import com.ljq.stock.alert.service.UserPushTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description: 公共模块控制层
 * @Author: junqiang.lu
 * @Date: 2022/8/2
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/common")
@Api(value = "公共模块", tags = "公共模块")
public class CommonController {

    @Resource
    private WechatConfig wechatConfig;
    @Resource
    private UserPushTypeService pushTypeService;
    @Resource
    private WxPusherConfig wxPusherConfig;


    /**
     * 获取微信小程序消息推送Token
     *
     * @return
     */
    @GetMapping(value = "/wechat/minMsg", produces = {MediaType.TEXT_PLAIN_VALUE})
    @ApiOperation(value = "获取微信小程序消息推送",  notes = "获取微信小程序消息推送")
    public String loginByWechatMini(@Validated WechatMiniMsgParam msgParam) {
        // TODO 签名验证

        return msgParam.getEchostr();
    }

    /**
     * WxPusher 回调接口
     *
     * @return
     */
    @PostMapping(value = "/wxpusher/callback", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "WxPusher 回调接口", notes = "WxPusher 回调接口")
    public ResponseEntity<ApiResult> wxPusherCallback(@Validated @RequestBody WxPusherCallbackParam callbackParam) {
        if (wxPusherConfig.getActionSubscribe().equalsIgnoreCase(callbackParam.getAction())) {
            return ResponseEntity.ok(pushTypeService.addWxPusher(callbackParam));
        }
        return ResponseEntity.ok(ApiResult.success());
    }

    /**
     * WxPusher 创建二维码
     *
     * @return
     */
    @PostMapping(value = "/wxpusher/qrcode/create", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "WxPusher 创建二维码")
    public ResponseEntity<ApiResult> wxPusherCreateQrCode() {
        return ResponseEntity.ok(pushTypeService.createWxPusherQrCode());
    }




}
