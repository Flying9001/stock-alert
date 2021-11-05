package com.ljq.stock.alert.common.util;

import cn.hutool.core.util.StrUtil;
import com.ljq.stock.alert.common.constant.MarketEnum;

import javax.validation.constraints.NotNull;

/**
 * @Description: 缓存 key 工具类
 * @Author: junqiang.lu
 * @Date: 2021/11/5
 */
public class CacheKeyUtil {

    private CacheKeyUtil(){
    }

    /**
     * 创建缓存 key
     *
     * @param prefix 前缀
     * @param key1
     * @param key2
     * @return
     */
    public static String create(@NotNull String prefix, @NotNull String key1, String key2) {
        StringBuilder keyBuilder = new StringBuilder(prefix);
        keyBuilder.append(key1);
        if (StrUtil.isNotBlank(key2)) {
            keyBuilder.append(key2);
        }
        return keyBuilder.toString();
    }

    /**
     * 创建股票源缓存键
     *
     * @param marketType
     * @param stockCode
     * @return
     */
    public static String createStockSourceKey(int marketType, String stockCode) {
        return create(MarketEnum.getMarketByType(marketType).getCode(), stockCode, null);
    }

}
