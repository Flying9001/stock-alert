package com.ljq.stock.alert.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 微信小程序登录认证返回结果
 * @Author: junqiang.lu
 * @Date: 2022/8/2
 */
@Data
public class WechatMiniLoginRespVo implements Serializable {

    private static final long serialVersionUID = 1149845431674869216L;

    /**
     * 用户在当前应用下的唯一标识
     */
    private String openid;

    /**
     * 会话秘钥
     */
    private String session_key;

    /**
     * 用户在当前平台下的唯一标识
     */
    private String unionid;

    /**
     * 错误码
     */
    private String errcode;

    /**
     * 错误信息
     */
    private String errmsg;

}
