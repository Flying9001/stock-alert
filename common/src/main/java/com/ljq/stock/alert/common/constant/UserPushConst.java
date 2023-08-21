package com.ljq.stock.alert.common.constant;

/**
 * @Description: 用户推送常量
 * @Author: junqiang.lu
 * @Date: 2023/8/1
 */
public class UserPushConst {

    private UserPushConst() {
    }

    /**
     * 单个用户最大允许设置的推送方式数量
     */
    public static final int USER_PUSH_TYPE_MAX = 5;

    /**
     * 用户通知推送类型
     * 1: 短信
     * 2: 邮件
     * 3: pushPlus
     * 31: pushPlus 微信公众号消息
     * 4: wxPusher
     */
    public static final int USER_PUSH_TYPE_SMS = 1;
    public static final int USER_PUSH_TYPE_EMAIL = 2;
    public static final int USER_PUSH_TYPE_PUSHPLUS = 3;
    public static final int USER_PUSH_TYPE_PUSHPLUS_WECHAT_PUBLIC = 31;
    public static final int USER_PUSH_TYPE_WXPUSHER = 4;



}
