package com.ljq.stock.alert.service.util;

import cn.hutool.core.util.PageUtil;
import cn.hutool.json.JSONUtil;
import com.ljq.stock.alert.common.config.StockApiConfig;
import com.ljq.stock.alert.model.entity.StockSourceEntity;
import com.ljq.stock.alert.model.vo.StockIndexVo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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


    @Test
    void getStockIndexLive() {
        StockApiConfig apiConfig = new StockApiConfig();
//        apiConfig.setApiSina("https://hq.sinajs.cn/list=");
//        apiConfig.setActive("sina");
        List<StockIndexVo> stockIndexVoList = StockUtil.getStockIndexLive(apiConfig);
        System.out.println("------------------------------------------");
        System.out.println(JSONUtil.toJsonStr(stockIndexVoList));

    }

    /**
     * 列表分割测试
     */
    @Test
    void listSubTest() {
        int countTotal = 105;
        int pageSize = 10;
        List<String> stringList = new ArrayList<>(countTotal);
        // list 伪造数据
        for (int i = 0; i < countTotal; i++) {
            stringList.add(i+1 + "");
        }

        // 分组解析数据
        int pageCount = PageUtil.totalPage(stringList.size(),pageSize);
        System.out.println("pageCount:" + pageCount);

        List<String> stringTempList;
        for (int i = 0; i < pageCount; i++) {
            int currentPoint = i * pageSize;
            if (i == pageCount - 1) {
                stringTempList = stringList.subList(currentPoint, stringList.size());
            } else {
                stringTempList = stringList.subList(currentPoint, currentPoint + pageSize);
            }
            System.out.println("i=" + i);
            System.out.println("list:" + stringTempList);
        }



    }
}