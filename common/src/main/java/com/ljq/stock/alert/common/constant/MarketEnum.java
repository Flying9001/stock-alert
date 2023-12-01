package com.ljq.stock.alert.common.constant;

import cn.hutool.core.util.StrUtil;
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
    SHANGHAI(1,"sh","上海证券交易所"),
    SHENZHEN(2, "sz","深圳证券交易所"),
    HK(3, "hk", "香港证券交易所"),
    USA(4, "us", "美国证券市场"),
    BEIJING(5, "bj", "北京证券交易所"),

    UNKNOWN(-1, "unknown", "未知证券交易所");

    /**
     * 市场类型
     */
    private int type;
    /**
     * 市场编码
     */
    private String code;
    /**
     * 市场名称
     */
    private String name;

    /**
     * 证券市场类型
     */
    public static final int MARKET_TYPE_SHANGHAI = 1;
    public static final int MARKET_TYPE_SHENZHEN = 2;
    public static final int MARKET_TYPE_HK = 3;
    public static final int MARKET_TYPE_USA = 4;
    public static final int MARKET_TYPE_BEIJING = 5;


    /**
     * 证券市场类型编码
     */
    public static final String MARKET_TYPE_CODE_SHANGHAI = "sh";
    public static final String MARKET_TYPE_CODE_SHENZHEN = "sz";
    public static final String MARKET_TYPE_CODE_HK = "hk";
    public static final String MARKET_TYPE_CODE_USA = "us";
    public static final String MARKET_TYPE_CODE_BEIJING = "bj";


    MarketEnum(Integer type, String code, String name) {
        this.type = type;
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
            case MARKET_TYPE_SHANGHAI:
                return SHANGHAI;
            case MARKET_TYPE_SHENZHEN:
                return SHENZHEN;
            case MARKET_TYPE_HK:
                return HK;
            case MARKET_TYPE_USA:
                return USA;
            case MARKET_TYPE_BEIJING:
                return BEIJING;
            default:
                return UNKNOWN;
        }
    }

    /**
     * 根据市场类型编码获取市场信息
     *
     * @param marketTypeCode
     * @return
     */
    public static MarketEnum getMarketByTypeCode(String marketTypeCode) {
        if (StrUtil.isBlank(marketTypeCode)) {
            return MarketEnum.UNKNOWN;
        }
        switch (marketTypeCode) {
            case MARKET_TYPE_CODE_SHANGHAI:
                return SHANGHAI;
            case MARKET_TYPE_CODE_SHENZHEN:
                return SHENZHEN;
            case MARKET_TYPE_CODE_HK:
                return HK;
            case MARKET_TYPE_CODE_USA:
                return USA;
            case MARKET_TYPE_CODE_BEIJING:
                return BEIJING;
            default:
                return UNKNOWN;
        }
    }

}
