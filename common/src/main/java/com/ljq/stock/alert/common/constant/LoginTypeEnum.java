package com.ljq.stock.alert.common.constant;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.ToString;

/**
 * @Description: 第三方登录类型枚举类
 * @Author: junqiang.lu
 * @Date: 2022/8/5
 */
@Getter
@ToString
public enum LoginTypeEnum {

    /**
     * 登录方式
     */
    WECHAT_MINI("WECHAT_MINI", "微信小程序"),



    UNKNOWN("unknown", "未知登录方式"),



    ;


    /**
     * 类型
     */
    private String type;

    /**
     * 说明
     */
    private String comment;

    LoginTypeEnum(String type, String comment) {
        this.type = type;
        this.comment = comment;
    }

    /**
     * 获取登录方式
     *
     * @param type
     * @return
     */
    public static LoginTypeEnum getLoginType(String type) {
        if (StrUtil.isBlank(type)) {
            return UNKNOWN;
        }
        for (LoginTypeEnum loginType : LoginTypeEnum.values()) {
            if (loginType.getType().equalsIgnoreCase(type)) {
                return loginType;
            }
        }
        return UNKNOWN;
    }

}
