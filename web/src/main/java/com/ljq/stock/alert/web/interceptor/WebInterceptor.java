package com.ljq.stock.alert.web.interceptor;

import cn.hutool.json.JSONUtil;
import com.ljq.stock.alert.common.api.ApiMsgEnum;
import com.ljq.stock.alert.common.api.ApiResult;
import com.ljq.stock.alert.common.constant.RequestConst;
import com.ljq.stock.alert.service.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

/**
 * @Description: 自定义拦截器
 * @Author: junqiang.lu
 * @Date: 2020/9/4
 */
@Slf4j
public class WebInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("requestPath: {}", request.getRequestURI());
        MDC.put(RequestConst.REQUEST_ID, UUID.randomUUID().toString());
        // Token 校验
        ApiMsgEnum apiMsgEnum = TokenUtil.validateToken(request, response);
        if (!Objects.equals(ApiMsgEnum.SUCCESS, apiMsgEnum)) {
            log.warn("{}", apiMsgEnum);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            PrintWriter writer = response.getWriter();
            writer.write(JSONUtil.toJsonStr(JSONUtil.parseObj(ApiResult.fail(apiMsgEnum), false)));
            return false;
        }
        // 拦截spring系统异常
        if (!checkSpringSysException(response)) {
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // post

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        // after

    }

    /**
     * 校验 spring 系统异常
     *
     * @param response
     * @return
     */
    private boolean checkSpringSysException(HttpServletResponse response) throws IOException {
        switch (response.getStatus()) {
            case HttpServletResponse.SC_NOT_FOUND:
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                response.getWriter().write((JSONUtil.toJsonStr(JSONUtil.parseObj(ApiResult
                        .fail(ApiMsgEnum.HTTP_NOT_FOUND),false))));
                log.warn("{}", ApiMsgEnum.HTTP_NOT_FOUND);
                break;
            case HttpServletResponse.SC_INTERNAL_SERVER_ERROR:
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                response.getWriter().write((JSONUtil.toJsonStr(JSONUtil.parseObj(ApiResult
                                .fail(ApiMsgEnum.UNKNOWN_ERROR),false))));
                log.warn("{}", ApiMsgEnum.UNKNOWN_ERROR);
                break;
            default: return true;
        }
        return false;
    }


}
