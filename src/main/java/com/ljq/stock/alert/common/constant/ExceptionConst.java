package com.ljq.stock.alert.common.constant;

/**
 * @Description: 常见异常类信息常量
 * @Author: junqiang.lu
 * @Date: 2020/9/5
 */
public class ExceptionConst {

    protected ExceptionConst() {
    }

    /**
     * 常见异常类名(依照常见顺序排序)
     * LOCAL_COMMON_EXCEPTION: 本地自定义公共异常,常手动抛出
     */
    public static final String LOCAL_COMMON_EXCEPTION = "com.ljq.stock.alert.common.exception.CommonException";
    /**
     * 参数绑定异常,前端请求参数到后台 Controller 时,参数绑定失败时抛出;常见于非 json 请求
     */
    public static final String PARAM_BIND_EXCEPTION = "org.springframework.validation.BindException";
    /**
     * 参数转换异常,eg: 后台参数格式为 Integer,前端传值为 String,则会抛出该异常;常见于 json 请求
     */
    public static final String PARAM_CONVERT_EXCEPTION = "org.springframework.http.converter" +
            ".HttpMessageNotReadableException";
    /**
     * 参数校验异常,通常在使用 @Validate 注解时会抛出
     */
    public static final String PARAM_VALIDATE_EXCEPTION = "org.springframework.web.bind.MethodArgumentNotValidException";
    /**
     * http请求方法异常,eg: 后台指定 POST 请求,前端使用 GET 请求,则会抛出该异常
     */
    public static final String HTTP_METHOD_NOT_SUPPORT_EXCEPTION = "org.springframework.web" +
            ".HttpRequestMethodNotSupportedException";
    /**
     * 网络请求参数格式不支持,通常前端传递的参数和后台指定的格式不一致时抛出,如后台指定 json 格式,前端传 form-data 格式时抛出
     */
    public static final String HTTP_MEDIA_TYPE_NOT_SUPPORT_EXCEPTION = "org.springframework.web." +
            "HttpMediaTypeNotSupportedException";
    /**
     * 缺失上传文件异常,当上传文件时前端没有传后台接口指定的文件名时抛出该异常;若前端传了文件名,但是实际没有文件上传,则不会抛出
     */
    public static final String MISSING_UPLOAD_FILE_EXCEPTION = "org.springframework.web.multipart.support." +
            "MissingServletRequestPartException";
    /**
     * 上传文件过大异常,实际上传文件大于后台限定的文件大小时抛出
     */
    public static final String MAX_UPLOAD_SIZE_EXCEPTION = "org.springframework.web.multipart" +
            ".MaxUploadSizeExceededException";
    /**
     * 无法创建数据库连接异常
     */
    public static final String CANNOT_CREATE_TRANSACTION_EXCEPTION = "org.springframework.transaction" +
            ".CannotCreateTransactionException";






}
