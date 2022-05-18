package com.ljq.stock.alert.common.constant;

import lombok.Getter;
import lombok.ToString;

/**
 * @Description: 验证码类型枚举类
 * @Author: junqiang.lu
 * @Date: 2021/5/19
 */
@ToString
@Getter
public enum CheckCodeTypeEnum {

    /**
     * 验证码操作类型
     */
    REGISTER(1, "注册"),
    SIGN_IN(2, "登录"),
    UPDATE_PASSCODE(3, "修改密码"),
    UPDATE_EMAIL(4, "修改邮箱"),

    /**
     * 未知操作
     */
    UNKNOWN(-1, "未知");

    /**
     * 编码
     */
    private int code;
    /**
     * 说明
     */
    private String description;


    CheckCodeTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 获取验证码类型
     *
     * @param code
     * @return
     */
    public static CheckCodeTypeEnum getType(int code) {
        for (CheckCodeTypeEnum typeEnum : CheckCodeTypeEnum.values()) {
            if (typeEnum.code == code) {
                return typeEnum;
            }
        }
        return CheckCodeTypeEnum.UNKNOWN;
    }


}
