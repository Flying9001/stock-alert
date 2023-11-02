package com.ljq.stock.alert.service.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.json.JSONUtil;
import com.ljq.stock.alert.common.api.ApiMsgEnum;
import com.ljq.stock.alert.common.constant.RequestConst;
import com.ljq.stock.alert.common.constant.TokenConst;
import com.ljq.stock.alert.common.util.JwtUtil;
import com.ljq.stock.alert.model.entity.AdminUserEntity;
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
     */
    public static String createToken(UserTokenVo userToken) {
        userToken.setTokenTime(System.currentTimeMillis());
        return JwtUtil.encode(TokenConst.TOKEN_KEY, JSONUtil.toJsonStr(userToken));
    }

    /**
     * 生成 Token
     *
     * @param userInfo
     * @return
     */
    public static String createToken(UserInfoEntity userInfo) {
        UserTokenVo userToken = new UserTokenVo();
        BeanUtil.copyProperties(userInfo, userToken, CopyOptions.create().ignoreNullValue());
        userToken.setTokenTime(System.currentTimeMillis());
        userToken.setAccountType(TokenConst.ACCOUNT_TYPE_USER);
        return JwtUtil.encode(TokenConst.TOKEN_KEY, JSONUtil.toJsonStr(userToken));
    }

    /**
     * 生成 Token
     * @param adminUser
     * @return
     */
    public static String createToken(AdminUserEntity adminUser) {
        UserTokenVo userToken = new UserTokenVo();
        BeanUtil.copyProperties(adminUser, userToken, CopyOptions.create().ignoreNullValue());
        userToken.setTokenTime(System.currentTimeMillis());
        userToken.setAccountType(TokenConst.ACCOUNT_TYPE_ADMIN);
        return JwtUtil.encode(TokenConst.TOKEN_KEY, JSONUtil.toJsonStr(userToken));
    }

    /**
     * 解析 Token
     *
     * @param token
     * @return
     */
    public static UserTokenVo decodeToken(String token) {
        String tokenValue = JwtUtil.decode(TokenConst.TOKEN_KEY, token);
        return JSONUtil.toBean(tokenValue, UserTokenVo.class);
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
            // 校验 Token 权限
            String requestPath = request.getRequestURI();
            switch (userToken.getAccountType()) {
                case TokenConst.ACCOUNT_TYPE_ADMIN:
                    if (!requestPath.startsWith(RequestConst.REQUEST_URL_PREFIX_ADMIN)) {
                        return ApiMsgEnum.USER_NO_PERMISSION;
                    }
                    break;
                case TokenConst.ACCOUNT_TYPE_USER:
                    if (!requestPath.startsWith(RequestConst.REQUEST_URL_PREFIX_APP)) {
                        return ApiMsgEnum.USER_NO_PERMISSION;
                    }
                    break;
                default: break;
            }
            // 刷新 Token
            if (subResult < TokenConst.TOKEN_REFRESH_TIME_MILLIS) {
                return ApiMsgEnum.SUCCESS;
            }
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
