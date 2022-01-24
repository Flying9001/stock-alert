package com.ljq.stock.alert.service.util;

import cn.hutool.json.JSONUtil;
import com.ljq.stock.alert.common.config.StockApiConfig;
import com.ljq.stock.alert.model.entity.StockSourceEntity;
import org.junit.jupiter.api.Test;

class StockUtilTest {

    @Test
    void getStockFromSina() {
        StockApiConfig apiConfig = new StockApiConfig();
//        apiConfig.setApiSina("https://hq.sinajs.cn/list=");
//        apiConfig.setActive("sina");
        String stockCode = "600009";

        int marketType = 1;
        StockSourceEntity stockSource = StockUtil.getStockLive(apiConfig, stockCode, marketType);
        System.out.println("------------------------------------------");
        System.out.println(JSONUtil.toJsonStr(stockSource));
    }

    @Test
    void getStockFromTencent() {
        StockApiConfig apiConfig = new StockApiConfig();
//        apiConfig.setApiTencent("https://sqt.gtimg.cn/q=");
//        apiConfig.setActive("tencent");
        String stockCode = "600009";
        int marketType = 1;
        StockSourceEntity stockSource = StockUtil.getStockLive(apiConfig, stockCode, marketType);
        System.out.println("------------------------------------------");
        System.out.println(JSONUtil.toJsonStr(stockSource));

    }

    @Test
    void getStockFromNetease() {
        StockApiConfig apiConfig = new StockApiConfig();
//        apiConfig.setApiNetease("https://api.money.126.net/data/feed/");
//        apiConfig.setActive("netease");
        String stockCode = "600009";
        int marketType = 1;
        StockSourceEntity stockSource = StockUtil.getStockLive(apiConfig, stockCode, marketType);
        System.out.println("------------------------------------------");
        System.out.println(JSONUtil.toJsonStr(stockSource));
    }


}