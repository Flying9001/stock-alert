package com.ljq.stock.alert.common.util;

import com.ljq.stock.alert.common.config.StockApiConfig;
import com.ljq.stock.alert.common.constant.MarketEnum;
import com.ljq.stock.alert.model.entity.StockSourceEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Description: 股票工具类
 * @Author: junqiang.lu
 * @Date: 2021/3/25
 */
public class StockUtil {

    private StockUtil(){
    }

    /**
     * 从新浪接口获取股票对象
     *
     * @param apiConfig 股票接口配置
     * @param stockCode 股票编码
     * @param marketType 股票市场类型
     * @return
     */
    public static StockSourceEntity getStockFromSina(StockApiConfig apiConfig, String stockCode, int marketType)
            throws IOException {
        // 获取股票市场类型
        MarketEnum marketEnum = MarketEnum.getMarketByType(marketType);
        if (Objects.equals(marketEnum, MarketEnum.UNKNOWN)) {
            // TODO 异常抛出
        }
        // 获取股票数据
        String pathParam = marketEnum.getCode() + stockCode;
        HttpResponse httpResponse = SimpleHttpClientUtil.doGet(apiConfig.getApiAddress(), pathParam, null, null);
        if (!Objects.equals(httpResponse.getStatusLine(), HttpStatus.SC_OK)) {
            // TODO 抛出异常
        }
        return createStock(EntityUtils.toString(httpResponse.getEntity(), "UTF-8"),
                marketType, stockCode);
    }

    /**
     * 从新浪接口获取股票对象列表
     *
     * @param apiConfig 股票接口配置
     * @param stockList 股票列表(包含股票代码,股票市场类型)
     * @return
     */
    public static List<StockSourceEntity> getStocksFromSina(StockApiConfig apiConfig, List<StockSourceEntity> stockList)
            throws IOException {
        StringBuilder queryParamBuilder = new StringBuilder();
        stockList.stream().forEach(stock -> {
            setMarketType(stock);
            queryParamBuilder.append(getQueryParam(stock));
        });
        HttpResponse httpResponse = SimpleHttpClientUtil.doGet(apiConfig.getApiAddress(),
                queryParamBuilder.toString(), null, null);
        if (!Objects.equals(httpResponse.getStatusLine(), HttpStatus.SC_OK)) {
            // TODO 抛出异常
        }
        String[] stockDataArr = EntityUtils.toString(httpResponse.getEntity(), "UTF-8").split(";");
        List<StockSourceEntity> resultStockList = new ArrayList<>();
        for (int i = 0; i < stockList.size(); i++) {
            resultStockList.add(createStock(stockDataArr[i], stockList.get(i).getMarketType(),
                    stockList.get(i).getStockCode()));
        }
        return resultStockList;
    }

    /**
     * 创建股票对象
     *
     * @param stockData 股票数据
     * @param marketType 市场类型
     * @param stockCode 股票编码
     * @return
     */
    public static StockSourceEntity createStock(String stockData, int marketType, String stockCode){
        String stockInfo = stockData;
        stockInfo = stockInfo.substring(stockInfo.indexOf("\"") + 1);
        stockInfo = stockInfo.substring(0,stockInfo.lastIndexOf("\""));
        String[] stockDataArr = stockInfo.split(",");
        StockSourceEntity stockSource = new StockSourceEntity();
        stockSource.setMarketType(marketType);
        stockSource.setStockCode(stockCode);
        stockSource.setCompanyName(stockDataArr[0]);
        stockSource.setTodayStartPrice(new BigDecimal(stockDataArr[1]));
        stockSource.setYesterdayEndPrice(new BigDecimal(stockDataArr[2]));
        stockSource.setCurrentPrice(new BigDecimal(stockDataArr[3]));
        stockSource.setIncrease(new BigDecimal(stockDataArr[4]));
        stockSource.setIncreasePer(new BigDecimal(stockDataArr[5]));
        stockSource.setTodayMaxPrice(new BigDecimal(stockDataArr[6]));
        stockSource.setTodayMinPrice(new BigDecimal(stockDataArr[7]));
        stockSource.setTradeNumber(Integer.parseInt(stockDataArr[8]));
        stockSource.setTradeAmount(new BigDecimal(stockDataArr[9]));
        stockSource.setDate(stockDataArr[30]);
        stockSource.setTime(stockDataArr[31]);
        return stockSource;
    }

    /**
     * 设置股票证券市场编码
     *
     * @param stock
     */
    private static void setMarketType(StockSourceEntity stock) {
        MarketEnum marketEnum = MarketEnum.getMarketByType(stock.getMarketType());
        if (Objects.equals(marketEnum, MarketEnum.UNKNOWN)) {
            // TODO 异常抛出
        }
        stock.setMarketTypeCode(marketEnum.getCode());
    }

    /**
     * 获取查询参数
     *
     * @param stock
     * @return
     */
    private static String getQueryParam(StockSourceEntity stock) {
        return stock.getMarketTypeCode() + stock.getStockCode() + ",";
    }




}
