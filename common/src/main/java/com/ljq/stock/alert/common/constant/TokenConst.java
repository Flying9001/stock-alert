package com.ljq.stock.alert.common.constant;

/**
 * @Description: Token 相关常量
 * @Author: junqiang.lu
 * @Date: 2019/12/3
 */
public class TokenConst {


    /**
     * Token headers 字段
     */
    public static final String TOKEN_HEADERS_FIELD = "Authorization";
    /**
     * token key
     */
    public static final String TOKEN_KEY = "tokenKey";
    /**
     * Token 刷新时间(单位: 毫秒)
     */
    public static final long TOKEN_REFRESH_TIME_MILLIS = 1000 * 60 * 60 * 2L;
    /**
     * token 有效期(单位: 毫秒)
     */
    public static final long TOKEN_EXPIRE_TIME_MILLIS = 1000 * 60 * 60 * 24 * 30L;

    /**
     * 账户类型
     * 1: 管理员
     * 2 普通用户
     */
    public static final int ACCOUNT_TYPE_ADMIN = 1;
    public static final int ACCOUNT_TYPE_USER = 2;





}
