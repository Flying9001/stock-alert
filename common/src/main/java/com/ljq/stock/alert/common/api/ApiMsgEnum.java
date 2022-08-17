package com.ljq.stock.alert.common.api;

import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * @Description: 接口返回结果信息枚举类
 * @Author: junqiang.lu
 * @Date: 2020/9/3
 */
@Getter
@ToString
public enum ApiMsgEnum {

    /**
     * 基础提示信息
     */
    SUCCESS("api.response.success","成功"),
    FAIL("api.response.fail","失败"),
    TOKEN_IS_NULL("api.response.tokenIsNull","Token 为空"),
    PARAM_BIND_EXCEPTION("api.response.paramBindException", "参数绑定错误,请输入正确格式的参数"),
    PARAM_CONVERT_ERROR("api.response.paramConvertError", "参数转换错误,请输入正确格式的参数"),
    PARAM_VALIDATE_EXCEPTION("api.response.paramValidateException","参数校验异常,请输入合法的参数"),
    HTTP_METHOD_NOT_SUPPORT_ERROR("api.response.httpMethodNotSupportError","HTTP 请求方式错误"),
    HTTP_MEDIA_TYPE_NOT_SUPPORT_ERROR("api.response.httpMediaTypeNotSupportError", "网络请求参数格式错误"),
    MISSING_UPLOAD_FILE_ERROR("api.response.missingUploadFileError", "缺失上传文件"),
    MAX_UPLOAD_SIZE_ERROR("api.response.maxUploadSizeError", "上传文件过大"),
    CANNOT_CREATE_TRANSACTION_ERROR("api.response.cannotCreateTractionError", "无法创建数据库连接"),
    HTTP_NOT_FOUND("api.response.httpNotFound","请求资源不存在"),

    /**
     * 股票数据
     */
    STOCK_UNKNOWN_MARKET_TYPE("api.response.stock.unknownMarketType", "未知股票证券市场"),
    STOCK_QUERY_ERROR("api.response.stock.queryError", "未查询到股票数据"),
    STOCK_DELETE_ERROR_USER_HAS_FOLLOWED("api.response.stock.userHasFollowed", "当前股票用户已经关注,不可删除"),

    /**
     * 用户信息
     */
    USER_EMAIL_REGISTERED("api.response.user.emailRegistered","邮箱已注册"),
    USER_MOBILE_PHONE_REGISTERED("api.response.user.mobilePhoneRegistered","手机号已注册"),
    USER_ACCOUNT_NOT_EXIST("api.response.user.accountAccountNotExist","账户不存在"),
    USER_PASSCODE_ERROR("api.response.user.passcodeError", "密码错误"),
    CHECK_CODE_MAIL_SEND_ERROR("api.response.user.checkCodeMailSendError","邮件验证码发送失败,请稍后重试"),
    CHECK_CODE_VALIDATE_ERROR("api.response.user.checkCodeValidateError", "验证码错误"),
    USER_PASSCODE_ENCRYPT_ERROR("api.response.user.passcodeEncryptError", "密码加密错误," +
            "请输入字母、数字与下划线的组合"),
    USER_TOKEN_NULL_ERROR("api.response.user.tokenNullError", "用户登录凭证不能为空"),
    USER_TOKEN_ERROR("api.response.user.tokenError", "用户登录信息已失效"),
    USER_LOGIN_WECHAT_MINI_ERROR("api.response.user.loginWechatMiniError", "微信小程序登录失败"),
    USER_WECHAT_MINI_NOT_BIND("api.response.user.wechatMiniNotBind", "用户未绑定微信小程序"),
    USER_WECHAT_MINI_BIND_REPEAT("api.response.user.wechatMiniBindRepeat", "用户已绑定微信,请勿重复操作"),
    USER_NO_PERMISSION("api.response.user.noPermission","用户无权限"),

    /**
     * 用户股票
     */
    USER_STOCK_EXISTED("api.response.userStock.existed","用户已经添加关注,请勿重复操作"),
    USER_STOCK_NOT_EXIST("api.response.userStock.notExist", "没有查询到用户关注的股票信息"),
    USER_STOCK_MAX_PRICE_LESS_THAN_MIN_PRICE("api.response.userStock.maxPriceLessThanMinPrice",
            "股价区间设置不合理,最高股价小于最低股价"),

    /**
     * 用户股票分组
     */
    USER_STOCK_GROUP_NOT_EXIST("api.response.userStockGroup.notExist", "用户股票分组不存在"),

    /**
     * 用户股票分组关联股票
     */
    STOCK_GROUP_STOCK_EXISTED("api.response.stockGroupStock.existed", "用户关注股票已经添加,请勿重复操作"),
    STOCK_GROUP_STOCK_NOT_EXIST("api.response.stockGroupStock.notExist", "股票分组中不存在当前股票"),

    /**
     * 消息提醒
     */
    ALERT_MESSAGE_NOT_EXIST("api.response.alterMessage.notExist", "提醒消息不存在"),

    /**
     * 管理员用户
     */
    ADMIN_USER_ACCOUNT_DISABLED("api.response.adminUser.accountDisabled", "账号被禁用"),


    /**
     * 未知异常
     */
    UNKNOWN_ERROR("api.response.unknownError", "未知异常");

    /**
     * 返回 key
     */
    private String key;
    /**
     * 默认提示信息
     */
    private String defaultMsg;

    ApiMsgEnum(String key, String defaultMsg) {
        this.key = key;
        this.defaultMsg = defaultMsg;
    }

    /**
     * 通过 key 获取返回对象
     *
     * @param key
     * @return
     */
    public static ApiMsgEnum getByKey(String key) {
        if (Objects.isNull(key) || key.trim().isEmpty()) {
            return ApiMsgEnum.UNKNOWN_ERROR;
        }
        for (ApiMsgEnum responseEnum : ApiMsgEnum.values()) {
            if (responseEnum.key.equalsIgnoreCase(key)) {
                return responseEnum;
            }
        }
        return ApiMsgEnum.UNKNOWN_ERROR;
    }




}
