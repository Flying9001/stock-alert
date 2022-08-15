package com.ljq.stock.alert.common.constant;

import lombok.Getter;

/**
 * @Description: 是否启用枚举类
 * @Author: junqiang.lu
 * @Date: 2022/8/15
 */
@Getter
public enum EnableEnum {

    /**
     * 是否启用
     */
    ENABLE(1, "是,启用"),
    DISABLE(0, "否,不启用"),


    ;

    /**
     * 代码
     */
    private int code;
    /**
     * 说明
     */
    private String comment;

    EnableEnum(int code, String comment) {
        this.code = code;
        this.comment = comment;
    }

}
