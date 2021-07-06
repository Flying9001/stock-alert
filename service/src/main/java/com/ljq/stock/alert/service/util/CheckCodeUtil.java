package com.ljq.stock.alert.service.util;

import cn.hutool.core.util.RandomUtil;
import com.ljq.stock.alert.common.component.RedisUtil;
import com.ljq.stock.alert.common.constant.CheckCodeTypeEnum;
import com.ljq.stock.alert.common.constant.UserConst;

import java.util.Objects;

/**
 * @Description: 验证码工具类
 * @Author: junqiang.lu
 * @Date: 2021/6/3
 */
public class CheckCodeUtil {

    private CheckCodeUtil() {
    }

    /**
     * 生成验证码
     *
     * @return
     */
    public static String generateCheckCode() {
        return RandomUtil.randomNumbers(6);
    }

    /**
     * 生成验证码缓存 key
     *
     * @param uniqueKey 唯一值,如邮箱、手机号
     * @param checkCodeType 验证码类型
     * @return
     */
    public static String generateCacheKey(String uniqueKey, CheckCodeTypeEnum checkCodeType) {
        return UserConst.CHECK_CODE_CACHE_KEY_PREFIX + uniqueKey + checkCodeType.getCode();
    }

    /**
     * 校验验证码有效性
     *
     * @param checkCode 验证码
     * @param cacheKey 验证码缓存 key
     * @param redisUtil redis 工具类
     * @return
     */
    public static boolean validateCheckCodeValidity(String checkCode, String cacheKey, RedisUtil redisUtil) {
        Object data = redisUtil.get(cacheKey);
        if (Objects.isNull(data)) {
            return false;
        }
        String checkCodeCache = String.valueOf(data);
        if (Objects.equals(checkCode, checkCodeCache)) {
            redisUtil.remove(cacheKey);
            return true;
        }
        return false;
    }
}
