package com.ljq.stock.alert.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;

import java.util.Date;

/**
 * @Description: JWT 工具类
 * @Author: junqiang.lu
 * @Date: 2019/12/3
 */
public class JwtUtil {

    /**
     * 默认秘钥
     */
    private final static String DEFAULT_SECRET = "9060D02CABC243698D9B47F6BE78791C";

    private JwtUtil(){
    }

    /**
     * 加密
     *
     * @param key
     * @param value
     * @return
     * @throws JWTCreationException
     */
    public static String encode(String key, String value) {
        return encode(key, value, 0);
    }

    /**
     * 加密
     *
     * @param key
     * @param value
     * @param expTime
     * @return
     * @throws JWTCreationException
     */
    public static String encode(String key, String value, long expTime) {
        return encode(null, key, value, expTime);
    }

    /**
     * 加密
     *
     * @param secret
     * @param key
     * @param value
     * @param expMillis
     * @return
     */
    public static String encode(String secret, String key, String value, long expMillis) {
        if (secret == null || secret.length() < 1) {
            secret = DEFAULT_SECRET;
        }
        Date expDate = null;
        if (expMillis > 1) {
            expDate = new Date(System.currentTimeMillis() + expMillis);
        }
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create()
                .withIssuer("auth0")
                .withClaim(key,value)
                .withExpiresAt(expDate)
                .sign(algorithm);
        return token;
    }

    /**
     * 解密
     *
     * @param key
     * @param encodedToken
     * @return
     * @throws JWTDecodeException
     */
    public static String decode(String key, String encodedToken) {
        return decode(null, key, encodedToken);
    }

    /**
     * 解密
     *
     * @param secret
     * @param key
     * @param encodedToken
     * @return
     */
    public static String decode(String secret, String key, String encodedToken) {
        if (secret == null || secret.length() < 1) {
            secret = DEFAULT_SECRET;
        }
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .build();
        return verifier.verify(encodedToken).getClaim(key).asString();
    }


}
