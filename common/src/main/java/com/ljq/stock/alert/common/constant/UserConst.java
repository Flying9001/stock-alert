package com.ljq.stock.alert.common.constant;

/**
 * @Description: 用户操作相关常量
 * @Author: junqiang.lu
 * @Date: 2021/5/24
 */
public class UserConst {

    private UserConst(){
    }

    /**
     * 验证码是要时间 10 分钟
     */
    public static final long CHECK_CODE_EXPIRE_TIME_SECOND = 600L;

    /**
     * 验证码缓存 key 前缀
     */
    public static final String CHECK_CODE_CACHE_KEY_PREFIX = "CHECK_CODE";
}
