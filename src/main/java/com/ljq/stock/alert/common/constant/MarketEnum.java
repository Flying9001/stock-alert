package com.ljq.stock.alert.common.constant;

import lombok.Getter;
import lombok.ToString;

/**
 * @Description: 股票市场枚举类
 * @Author: junqiang.lu
 * @Date: 2021/3/24
 */
@Getter
@ToString
public enum  MarketEnum {

    /**
     * 证券交易所
     */
    SHANGHAI("sh","上海证券交易所"),
    SHENZHEN("sz","深圳证券交易所"),
    HK("hk", "香港证券交易所"),
    USA("us", "美国证券市场"),

    UNKNOWN("unknown", "未知证券交易所");

    /**
     * 市场编码
     */
    private String code;
    /**
     * 市场名称
     */
    private String name;

    private MarketEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 根据市场类型获取市场信息
     *
     * @param marketType
     * @return
     */
    public static MarketEnum getMarketByType(int marketType) {
        switch (marketType) {
            case StockConst.MARKET_TYPE_SHANGHAI: return MarketEnum.SHANGHAI;
            case StockConst.MARKET_TYPE_SHENZHEN: return MarketEnum.SHENZHEN;
            case StockConst.MARKET_TYPE_HK: return MarketEnum.HK;
            case StockConst.MARKET_TYPE_USA: return MarketEnum.USA;
            default: return MarketEnum.UNKNOWN;
        }
    }

}
