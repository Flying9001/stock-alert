package com.ljq.stock.alert.service.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljq.stock.alert.common.api.ApiMsgEnum;
import com.ljq.stock.alert.common.constant.TokenConst;
import com.ljq.stock.alert.common.util.JwtUtil;
import com.ljq.stock.alert.model.entity.UserInfoEntity;
import com.ljq.stock.alert.model.vo.UserTokenVo;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 用户 token 工具类
 * @Author: junqiang.lu
 * @Date: 2021/6/15
 */
@Slf4j
public class TokenUtil {

    private TokenUtil() {
    }

    /**
     * 生成 Token
     *
     * @param userToken
     * @return
     * @throws JsonProcessingException
     */
    public static String createToken(UserTokenVo userToken) throws JsonProcessingException {
        userToken.setTokenTime(System.currentTimeMillis());
        return JwtUtil.encode(TokenConst.TOKEN_KEY, new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .writeValueAsString(userToken));
    }

    /**
     * 生成 Token
     *
     * @param userInfo
     * @return
     * @throws JsonProcessingException
     */
    public static String createToken(UserInfoEntity userInfo) throws JsonProcessingException {
        UserTokenVo userToken = new UserTokenVo();
        BeanUtil.copyProperties(userInfo, userToken, CopyOptions.create().ignoreNullValue());
        userToken.setTokenTime(System.currentTimeMillis());
        return JwtUtil.encode(TokenConst.TOKEN_KEY, new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .writeValueAsString(userToken));
    }

    /**
     * 解析 Token
     *
     * @param token
     * @return
     * @throws JsonProcessingException
     */
    public static UserTokenVo decodeToken(String token) throws JsonProcessingException {
        String tokenValue = JwtUtil.decode(TokenConst.TOKEN_KEY, token);
        return new ObjectMapper().readValue(tokenValue, UserTokenVo.class);
    }

    /**
     * 校验用户 Token
     *
     * @param request
     * @param response
     * @return
     */
    public static ApiMsgEnum validateToken(HttpServletRequest request, HttpServletResponse response) {
        UserTokenVo userToken = null;
        try {
            String token = request.getHeader(TokenConst.TOKEN_HEADERS_FIELD);
            if (token == null || token.length() < 1) {
                return ApiMsgEnum.USER_TOKEN_NULL_ERROR;
            }
            // 解析 Token
            userToken = TokenUtil.decodeToken(token);
            // 校验 Token 有效性
            long subResult = System.currentTimeMillis() - userToken.getTokenTime();
            if (subResult >= TokenConst.TOKEN_EXPIRE_TIME_MILLIS) {
                return ApiMsgEnum.USER_TOKEN_ERROR;
            }
            if (subResult < TokenConst.TOKEN_REFRESH_TIME_MILLIS) {
                return ApiMsgEnum.SUCCESS;
            }
            // 刷新 Token
            response.setHeader(TokenConst.TOKEN_HEADERS_FIELD, TokenUtil.createToken(userToken));
        } catch (Exception e) {
            log.error("Token 校验失败,{}:{}", e.getClass().getName(), e.getMessage());
            return ApiMsgEnum.USER_TOKEN_ERROR;
        } finally {
            // 保存 Token 至上下文环境
            SessionUtil.currentSession().setUserToken(userToken);
        }
        return ApiMsgEnum.SUCCESS;
    }

}
