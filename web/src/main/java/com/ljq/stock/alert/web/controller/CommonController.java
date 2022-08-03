package com.ljq.stock.alert.web.controller;

import com.ljq.stock.alert.common.config.WechatConfig;
import com.ljq.stock.alert.model.param.common.WechatMiniMsgParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    private WechatConfig wechatConfig;

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

}
