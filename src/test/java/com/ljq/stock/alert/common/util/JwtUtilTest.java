package com.ljq.stock.alert.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljq.stock.alert.model.entity.UserInfoEntity;
import org.junit.jupiter.api.Test;

class JwtUtilTest {

    @Test
    void encode() {
        String key = "userName";
        String value = new UserInfoEntity().toString();
        String token = JwtUtil.encode(key, value, 30 * 1000);
        System.out.println("token: " + token);

    }

    @Test
    void decode() {
        String key = "userName";
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsInVzZXJOYW1lIjoiVXNlckluZm9FbnRpdHkoYWNjb3VudD1udWxsLCBuaWNrTmFtZT1udWxsLCBwYXNzY29kZT1udWxsLCBtb2JpbGVQaG9uZT1udWxsLCBlbWFpbD1udWxsLCB0b2tlbj1udWxsKSIsImV4cCI6MTYyMzczNTkyNn0.VVFV4SBg0onaRg4oY9hJeboK35p4qouUfw0djXNWOjw";
        String value = JwtUtil.decode(key, token);
        System.out.println("value: " + value);
    }

    @Test
    void encode2() throws JsonProcessingException {
        String key = "userToken";
        String value = new ObjectMapper().writeValueAsString(new UserInfoEntity());
        System.out.println("value: " + value);
        String token = JwtUtil.encode(key, value);
        System.out.println("token: " + token);
    }

    @Test
    void decode2() throws JsonProcessingException {
        String key = "userToken";
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyVG9rZW4iOiJ7XCJpZFwiOm51bGwsXCJjcmVhdGVUaW1lXCI6bnVsbCxcInVwZGF0ZVRpbWVcIjpudWxsLFwiYWNjb3VudFwiOm51bGwsXCJuaWNrTmFtZVwiOm51bGwsXCJwYXNzY29kZVwiOm51bGwsXCJtb2JpbGVQaG9uZVwiOm51bGwsXCJlbWFpbFwiOm51bGwsXCJ0b2tlblwiOm51bGx9IiwiaXNzIjoiYXV0aDAifQ.WoQZ3bQtjMLOmeOfZKe5eUvZMR2aWgwTSIndl5bGs24";
        String value = JwtUtil.decode(key, token);
        System.out.println("value: " + value);
        UserInfoEntity userInfoEntity = new ObjectMapper().readValue(value, UserInfoEntity.class);
        System.out.println("userInfoEntity" + userInfoEntity);
    }


}