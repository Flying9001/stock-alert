package com.ljq.stock.alert.web.interceptor;

import com.ljq.stock.alert.common.api.ApiMsgEnum;
import com.ljq.stock.alert.common.api.ApiResult;
import com.ljq.stock.alert.common.constant.ExceptionConst;
import com.ljq.stock.alert.common.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Description: 全局异常处理
 * @Author: junqiang.lu
 * @Date: 2020/9/5
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 默认栈轨迹深度
     */
    private static final int DEFAULT_STACK_TRACE_DEPTH = 3;
    @Value("${exceptionLogStackDepth: 3}")
    private int exceptionLogStackDepth;

    @ExceptionHandler(value = {CommonException.class,Exception.class})
    public ResponseEntity<ApiResult<String>> exceptionHandler(Exception e, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ApiResult<String> apiResult;
        switch (e.getClass().getName()) {
            case ExceptionConst.LOCAL_COMMON_EXCEPTION:
                apiResult = ApiResult.fail(ApiMsgEnum.getByKey(((CommonException) e).getKey()));
                break;
            case ExceptionConst.PARAM_BIND_EXCEPTION:
                apiResult = processBindException(e, e.getMessage());
                break;
            case ExceptionConst.PARAM_CONVERT_EXCEPTION:
                apiResult = ApiResult.fail(ApiMsgEnum.PARAM_CONVERT_ERROR);
                break;
            case ExceptionConst.PARAM_VALIDATE_EXCEPTION:
                apiResult = ApiResult.fail(ApiMsgEnum.PARAM_VALIDATE_EXCEPTION.getKey(),
                        ((MethodArgumentNotValidException)e).getBindingResult().getAllErrors().get(0)
                                .getDefaultMessage());
                break;
            case ExceptionConst.HTTP_METHOD_NOT_SUPPORT_EXCEPTION:
                apiResult = ApiResult.fail(ApiMsgEnum.HTTP_METHOD_NOT_SUPPORT_ERROR);
                break;
            case ExceptionConst.HTTP_MEDIA_TYPE_NOT_SUPPORT_EXCEPTION:
                apiResult = ApiResult.fail(ApiMsgEnum.HTTP_MEDIA_TYPE_NOT_SUPPORT_ERROR);
                break;
            case ExceptionConst.MISSING_UPLOAD_FILE_EXCEPTION:
                apiResult = ApiResult.fail(ApiMsgEnum.MISSING_UPLOAD_FILE_ERROR);
                break;
            case ExceptionConst.MAX_UPLOAD_SIZE_EXCEPTION:
                apiResult = ApiResult.fail(ApiMsgEnum.MAX_UPLOAD_SIZE_ERROR);
                break;
            case ExceptionConst.CANNOT_CREATE_TRANSACTION_EXCEPTION:
                apiResult = ApiResult.fail(ApiMsgEnum.CANNOT_CREATE_TRANSACTION_ERROR);
                break;
            default:
                log.warn("exception class: {}", e.getClass().getName());
                apiResult = ApiResult.fail(ApiMsgEnum.UNKNOWN_ERROR);
                break;
        }
        log.error("key:{},\n\t msg:{},\n\t trace:{}",apiResult.getKey(), e.getMessage(),
                getStackTraceInfo(e.getStackTrace(), exceptionLogStackDepth));
        return new ResponseEntity<>(apiResult,
                headers, HttpStatus.BAD_REQUEST);
    }

    /**
     * 获取栈轨迹信息
     *
     * @param traceElements 栈轨迹元素数组
     * @param depth 读取深度
     * @return
     */
    private String getStackTraceInfo(StackTraceElement[] traceElements, int depth) {
        depth = depth > 0 ? depth : DEFAULT_STACK_TRACE_DEPTH;
        if (Objects.isNull(traceElements) || traceElements.length < 1) {
            return null;
        }
        if (traceElements.length < depth) {
            depth = traceElements.length;
        }
        StringBuilder traceBuilder = new StringBuilder("\n\t");
        for (int i = 0; i < depth - 1; i++) {
            traceBuilder.append(traceElements[i]).append("\n\t");
        }
        traceBuilder.append(traceElements[depth]);
        return traceBuilder.toString();
    }

    /**
     * 处理参数绑定异常
     *
     * @param errorMsg 异常消息
     * @return
     */
    private ApiResult<String> processBindException(Exception e,String errorMsg) {
        String typeErrorKey = "typeMismatch";
        if (errorMsg.contains(typeErrorKey)) {
            return ApiResult.fail(ApiMsgEnum.PARAM_BIND_EXCEPTION);
        }
        return ApiResult.fail(ApiMsgEnum.PARAM_BIND_EXCEPTION.getKey(), ((BindException)e).getBindingResult()
                .getAllErrors().get(0).getDefaultMessage());
    }

}
