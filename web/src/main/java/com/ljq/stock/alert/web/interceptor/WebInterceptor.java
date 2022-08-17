package com.ljq.stock.alert.web.interceptor;

import cn.hutool.json.JSONUtil;
import com.ljq.stock.alert.common.api.ApiMsgEnum;
import com.ljq.stock.alert.common.api.ApiResult;
import com.ljq.stock.alert.common.constant.RequestConst;
import com.ljq.stock.alert.service.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
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
            response.setContentType("application/json; charset=utf-8");
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSONUtil.toJsonStr(JSONUtil.parseObj(ApiResult.fail(apiMsgEnum), false)));
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


}
