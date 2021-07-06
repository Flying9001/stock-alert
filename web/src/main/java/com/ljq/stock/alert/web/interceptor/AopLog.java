package com.ljq.stock.alert.web.interceptor;

import cn.hutool.core.text.CharSequenceUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ljq.stock.alert.common.config.AopLogConfig;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @Description: Controller 出入参日志记录
 * @Author: junqiang.lu
 * @Date: 2020/9/4
 */
@Slf4j
@Aspect
@Component
public class AopLog {

    private static final String UNKNOWN_FIELD = "unknown";

    /**
     * controller 层切点
     */
    @Pointcut("execution(* com.ljq.stock.alert.web.controller.*.*(..))")
    public void controllerPointcut() {
        // 定义切点
    }

    /**
     * controller 层出入参日志记录
     *
     * @param joinPoint 切点
     * @return
     */
    @Around(value = "controllerPointcut()")
    public Object controllerLogAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String uuid = UUID.randomUUID().toString();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        // 获取切点请求参数(class,method)
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        AopLogConfig logConfig = method.getAnnotation(AopLogConfig.class);
        // 获取 request 中包含的请求参数
        String params = getRequestParams(request, joinPoint);
        if (Objects.isNull(logConfig)) {
            // 入参日志
            log.info("[AOP-LOG-START]\n\trequestID: {}\n\tIP: {}\n\tcontentType:{}\n\turl: {}\n\t" +
                            "method: {}\n\tparams: {}\n\ttargetClassAndMethod: {}#{}", uuid, getIpAddress(request),
                    request.getHeader(HttpHeaders.CONTENT_TYPE), request.getRequestURL(), request.getMethod(), params,
                    method.getDeclaringClass().getName(), method.getName());
            // 出参日志
            Object result = joinPoint.proceed();
            log.info("[AOP-LOG-END]\n\trequestID: {}\n\turl: {}\n\tresponse: {}",
                    uuid, request.getRequestURL(), result);
            return result;
        }
        if (!logConfig.ignoreInput()) {
            // 入参日志
            log.info("[AOP-LOG-START]\n\trequestID: {}\n\tIP: {}\n\tcontentType:{}\n\turl: {}\n\t" +
                            "method: {}\n\tparams: {}\n\ttargetClassAndMethod: {}#{}", uuid, getIpAddress(request),
                    request.getHeader(HttpHeaders.CONTENT_TYPE), request.getRequestURL(), request.getMethod(), params,
                    method.getDeclaringClass().getName(), method.getName());
        }
        Object result = joinPoint.proceed();
        if (!logConfig.ignoreOutput()) {
            // 出参日志
            log.info("[AOP-LOG-END]\n\trequestID: {}\n\turl: {}\n\tresponse: {}",
                    uuid, request.getRequestURL(), result);
            return result;
        }
        return result;
    }

    /**
     * 获取请求参数
     *
     * @param request
     * @param joinPoint
     * @return
     */
    private String getRequestParams(HttpServletRequest request, ProceedingJoinPoint joinPoint) throws JsonProcessingException {
        StringBuilder params = new StringBuilder();
        // 获取 request parameter 中的参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (!CollectionUtils.isEmpty(parameterMap)) {
            parameterMap.forEach((k, v) ->
                params.append(k + " = " + Arrays.toString(v) + ";")
            );
        }
        if (HttpMethod.POST.name().equalsIgnoreCase(request.getMethod()) ||
                HttpMethod.PUT.name().equalsIgnoreCase(request.getMethod()) ||
                HttpMethod.DELETE.name().equalsIgnoreCase(request.getMethod())) {
            // 获取非 request parameter 中的参数
            Object[] objects = joinPoint.getArgs();
            for (Object arg : objects) {
                if (arg == null) {
                    break;
                }
                String className = arg.getClass().getName().toLowerCase();
                String contentType = request.getHeader(HttpHeaders.CONTENT_TYPE);
                // 文件参数,上传文件信息
                if (className.contains("MultipartFile".toLowerCase())) {
                    MultipartFile multipartFile = (MultipartFile) arg;
                    params.append("fileSize = " + multipartFile.getSize() + ";");
                    params.append("fileContentType = " + multipartFile.getContentType() + ";");
                    params.append("fieldName = " + multipartFile.getName() + ";");
                    params.append("fileOriginalName = " + multipartFile.getOriginalFilename() + ";");
                }
                if (contentType != null && contentType.contains("application/json")) {
                    // json 参数
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                    mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
                    params.append(mapper.writeValueAsString(arg));
                }
            }
        }
        return params.toString();
    }

    /**
     * 获取客户端请求 ip
     *
     * @param request
     * @return
     */
    private static String getIpAddress(HttpServletRequest request) {
        String xip = request.getHeader("X-Real-IP");
        String xFor = request.getHeader("X-Forwarded-For");
        if(CharSequenceUtil.isNotEmpty(xFor) && !UNKNOWN_FIELD.equalsIgnoreCase(xFor)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = xFor.indexOf(",");
            if(index != -1){
                return xFor.substring(0,index);
            }else{
                return xFor;
            }
        }
        xFor = xip;
        if(CharSequenceUtil.isNotEmpty(xFor) && !UNKNOWN_FIELD.equalsIgnoreCase(xFor)){
            return xFor;
        }
        if (CharSequenceUtil.isBlank(xFor) || UNKNOWN_FIELD.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("Proxy-Client-IP");
        }
        if (CharSequenceUtil.isBlank(xFor) || UNKNOWN_FIELD.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (CharSequenceUtil.isBlank(xFor) || UNKNOWN_FIELD.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (CharSequenceUtil.isBlank(xFor) || UNKNOWN_FIELD.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (CharSequenceUtil.isBlank(xFor) || UNKNOWN_FIELD.equalsIgnoreCase(xFor)) {
            xFor = request.getRemoteAddr();
        }
        return xFor;
    }





}
