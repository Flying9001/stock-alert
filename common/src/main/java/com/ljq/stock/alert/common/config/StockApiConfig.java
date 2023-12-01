package com.ljq.stock.alert.common.config;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 股票数据源配置
 * @Author: junqiang.lu
 * @Date: 2021/3/24
 */
@Getter
@ToString
@Configuration
public class StockApiConfig {

    /**
     * 新浪接口地址
     */
    @Value(value = "${stockApi.apiSina: https://hq.sinajs.cn/list=}")
    private String apiSina;
    /**
     * 腾讯接口地址
     */
    @Value(value = "${stockApi.apiTencent: https://sqt.gtimg.cn/q=}")
    private String apiTencent;
    /**
     * 网易接口地址
     */
    @Value(value = "${stockApi.apiNetease: https://api.money.126.net/data/feed/}")
    private String apiNetease;
    /**
     * 启用的接口(新浪:sina;腾讯:tencent;网易:netease)
     */
    @Value(value = "${stockApi.active: sina}")
    private String active;

    /**
     * 新浪获取市场所有股票接口地址
     */
    @Value(value = "${stockApi.apiAllStockSina: https://vip.stock.finance.sina.com.cn/quotes_service/api/" +
            "json_v2.php/Market_Center.getHQNodeData}")
    private String apiAllStockSina;

    /**
     * 麦蕊获取市场所有股票接口地址
     */
    @Value(value = "${stockApi.apiAllStockMyData: https://api.mairui.club/hslt/list/}")
    private String apiAllStockMyData;

    /**
     * 麦蕊获取市场所有股票接口地址
     */
    @Value(value = "${stockApi.apiMyDataLicence: xxx}")
    private String apiMyDataLicence;

}
