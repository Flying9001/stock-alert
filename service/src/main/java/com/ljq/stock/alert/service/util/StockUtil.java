package com.ljq.stock.alert.service.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ljq.stock.alert.common.api.ApiMsgEnum;
import com.ljq.stock.alert.common.config.StockApiConfig;
import com.ljq.stock.alert.common.constant.MarketEnum;
import com.ljq.stock.alert.common.constant.StockConst;
import com.ljq.stock.alert.common.exception.CommonException;
import com.ljq.stock.alert.common.util.SimpleHttpClientUtil;
import com.ljq.stock.alert.model.entity.StockSourceEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @Description: 股票工具类
 * @Author: junqiang.lu
 * @Date: 2021/3/25
 */
@Slf4j
public class StockUtil {

    private StockUtil(){
    }

    /**
     * 获取股票实时股价信息
     *
     * @param apiConfig
     * @param stockCode
     * @param marketType
     * @return
     */
    public static StockSourceEntity getStockLive(StockApiConfig apiConfig, String stockCode, int marketType) {
        switch (apiConfig.getActive()) {
            case StockConst.STOCK_API_SINA:
                return getStockFromSina(apiConfig, stockCode, marketType);
            case StockConst.STOCK_API_TENCENT:
                return getStockFromTencent(apiConfig, stockCode, marketType);
            case StockConst.STOCK_API_NETEASE:
                return getStockFromNetease(apiConfig, stockCode, marketType);
            default:
                break;
        }
        return null;
    }

    /**
     * 获取股票列表实时股价
     *
     * @param apiConfig 股票接口配置
     * @param stockList 股票列表(包含股票代码,股票市场类型)
     * @return
     */
    public static List<StockSourceEntity> getStocksLive(StockApiConfig apiConfig, List<StockSourceEntity> stockList){
        if (CollectionUtil.isEmpty(stockList)) {
            return Collections.emptyList();
        }
        switch (apiConfig.getActive()) {
            case StockConst.STOCK_API_SINA:
                return getStocksFromSina(apiConfig, stockList);
            case StockConst.STOCK_API_TENCENT:
                return getStocksFromTencent(apiConfig, stockList);
            case StockConst.STOCK_API_NETEASE:
                return getStocksFromNetease(apiConfig, stockList);
            default:
                break;
        }
        return Collections.emptyList();
    }

    /**
     * 从新浪接口获取股票对象
     *
     * @param apiConfig 股票接口配置
     * @param stockCode 股票编码
     * @param marketType 股票市场类型
     * @return
     */
    private static StockSourceEntity getStockFromSina(StockApiConfig apiConfig, String stockCode, int marketType) {
        // 获取股票市场类型
        MarketEnum marketEnum = MarketEnum.getMarketByType(marketType);
        // 获取股票数据
        String pathParam = getQueryParam(marketEnum.getCode(), stockCode);
        Map<String, String> headersMap = new HashMap<>(8);
        headersMap.put("Referer", "https://finance.sina.com.cn/");
        HttpResponse httpResponse = null;
        try {
            httpResponse = SimpleHttpClientUtil.doGet(apiConfig.getApiSina(), pathParam, null, headersMap);
            if (!Objects.equals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK)) {
                throw new CommonException(ApiMsgEnum.STOCK_QUERY_ERROR);
            }
            return createStockFromSina(EntityUtils.toString(httpResponse.getEntity(), "UTF-8"),
                    marketType, stockCode);
        } catch (IOException e) {
            log.error("{}", e.getMessage());
            throw new CommonException(ApiMsgEnum.UNKNOWN_ERROR);
        } catch (ArrayIndexOutOfBoundsException e2) {
            throw new CommonException(ApiMsgEnum.STOCK_QUERY_ERROR);
        }
    }

    /**
     * 从新浪接口获取股票对象列表
     *
     * @param apiConfig 股票接口配置
     * @param stockList 股票列表(包含股票代码,股票市场类型)
     * @return
     */
    private static List<StockSourceEntity> getStocksFromSina(StockApiConfig apiConfig,
                                                            List<StockSourceEntity> stockList){
        StringBuilder queryParamBuilder = new StringBuilder();
        stockList.stream().forEach(stock -> {
            setMarketType(stock);
            queryParamBuilder.append(getQueryParam(stock.getMarketTypeCode(), stock.getStockCode())).append(",");
        });
        Map<String, String> headersMap = new HashMap<>(8);
        headersMap.put("Referer", "https://finance.sina.com.cn/");
        HttpResponse httpResponse = null;
        try {
            httpResponse = SimpleHttpClientUtil.doGet(apiConfig.getApiSina(),
                    queryParamBuilder.toString(), null, headersMap);
            if (!Objects.equals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK)) {
                throw new CommonException(ApiMsgEnum.STOCK_QUERY_ERROR);
            }
            String[] stockDataArr = EntityUtils.toString(httpResponse.getEntity(), "UTF-8").split(";");
            List<StockSourceEntity> resultStockList = new ArrayList<>();
            for (int i = 0; i < stockList.size(); i++) {
                resultStockList.add(createStockFromSina(stockDataArr[i], stockList.get(i).getMarketType(),
                        stockList.get(i).getStockCode(), stockList.get(i).getId()));
            }
            return resultStockList;
        } catch (IOException e) {
            log.error("{}", e.getMessage());
            throw new CommonException(ApiMsgEnum.UNKNOWN_ERROR);
        } catch (ArrayIndexOutOfBoundsException e2) {
            throw new CommonException(ApiMsgEnum.STOCK_QUERY_ERROR);
        }
    }

    /**
     * 从腾讯接口获取实时股票对象
     *
     * @param apiConfig 股票接口配置
     * @param stockCode 股票编码
     * @param marketType 股票市场类型
     * @return
     */
    private static StockSourceEntity getStockFromTencent(StockApiConfig apiConfig, String stockCode, int marketType) {
        // 获取股票市场类型
        MarketEnum marketEnum = MarketEnum.getMarketByType(marketType);
        // 获取股票数据
        String pathParam = getQueryParam(marketEnum.getCode(), stockCode);
        Map<String, String> headersMap = new HashMap<>(8);
        headersMap.put("Referer", "https://stockapp.finance.qq.com/");
        HttpResponse httpResponse = null;
        try {
            httpResponse = SimpleHttpClientUtil.doGet(apiConfig.getApiTencent(), pathParam, null, headersMap);
            if (!Objects.equals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK)) {
                throw new CommonException(ApiMsgEnum.STOCK_QUERY_ERROR);
            }
            return createStockFromTencent(EntityUtils.toString(httpResponse.getEntity(), "UTF-8"),
                    marketType, stockCode);
        } catch (IOException e) {
            log.error("{}", e.getMessage());
            throw new CommonException(ApiMsgEnum.UNKNOWN_ERROR);
        } catch (ArrayIndexOutOfBoundsException e2) {
            throw new CommonException(ApiMsgEnum.STOCK_QUERY_ERROR);
        }
    }

    /**
     * 从腾讯接口获取股票对象列表
     *
     * @param apiConfig 股票接口配置
     * @param stockList 股票列表(包含股票代码,股票市场类型)
     * @return
     */
    private static List<StockSourceEntity> getStocksFromTencent(StockApiConfig apiConfig,
                                                               List<StockSourceEntity> stockList){
        StringBuilder queryParamBuilder = new StringBuilder();
        stockList.stream().forEach(stock -> {
            setMarketType(stock);
            queryParamBuilder.append(getQueryParam(stock.getMarketTypeCode(), stock.getStockCode())).append(",");
        });
        Map<String, String> headersMap = new HashMap<>(8);
        headersMap.put("Referer", "https://stockapp.finance.qq.com/");
        HttpResponse httpResponse = null;
        try {
            httpResponse = SimpleHttpClientUtil.doGet(apiConfig.getApiTencent(),queryParamBuilder.toString(),
                    null, headersMap);
            if (!Objects.equals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK)) {
                throw new CommonException(ApiMsgEnum.STOCK_QUERY_ERROR);
            }
            String[] stockDataArr = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8).split(";");
            List<StockSourceEntity> resultStockList = new ArrayList<>();
            for (int i = 0; i < stockList.size(); i++) {
                resultStockList.add(createStockFromTencent(stockDataArr[i], stockList.get(i).getMarketType(),
                        stockList.get(i).getStockCode(), stockList.get(i).getId()));
            }
            return resultStockList;
        } catch (IOException e) {
            log.error("{}", e.getMessage());
            throw new CommonException(ApiMsgEnum.UNKNOWN_ERROR);
        } catch (ArrayIndexOutOfBoundsException e2) {
            throw new CommonException(ApiMsgEnum.STOCK_QUERY_ERROR);
        }
    }

    /**
     * 从网易接口获取实时股票对象
     *
     * @param apiConfig 股票接口配置
     * @param stockCode 股票编码
     * @param marketType 股票市场类型
     * @return
     */
    private static StockSourceEntity getStockFromNetease(StockApiConfig apiConfig, String stockCode, int marketType) {
        // 组装请求参数
        String pathParam = getQueryParamForNetease(marketType, stockCode);
        Map<String, String> headersMap = new HashMap<>(8);
        headersMap.put("Referer", "https://money.163.com/");
        HttpResponse httpResponse = null;
        try {
            httpResponse = SimpleHttpClientUtil.doGet(apiConfig.getApiNetease(), pathParam, null, headersMap);
            if (!Objects.equals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK)) {
                throw new CommonException(ApiMsgEnum.STOCK_QUERY_ERROR);
            }
            String stockData = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
            stockData = stockData.substring(stockData.indexOf("(") + 12, stockData.lastIndexOf(")") - 1);
            return createStockFromNetease(stockData,marketType, stockCode);
        } catch (IOException e) {
            log.error("{}", e.getMessage());
            throw new CommonException(ApiMsgEnum.UNKNOWN_ERROR);
        } catch (ArrayIndexOutOfBoundsException e2) {
            throw new CommonException(ApiMsgEnum.STOCK_QUERY_ERROR);
        }
    }

    /**
     * 从网易接口获取股票对象列表
     *
     * @param apiConfig 股票接口配置
     * @param stockList 股票列表(包含股票代码,股票市场类型)
     * @return
     */
    private static List<StockSourceEntity> getStocksFromNetease(StockApiConfig apiConfig,
                                                               List<StockSourceEntity> stockList){
        StringBuilder queryParamBuilder = new StringBuilder();
        stockList.stream().forEach(stock ->
            queryParamBuilder.append(getQueryParamForNetease(stock.getMarketType(),stock.getStockCode())).append(",")
        );
        Map<String, String> headersMap = new HashMap<>(16);
        headersMap.put("Referer", "https://money.163.com/");
        HttpResponse httpResponse = null;
        try {
            httpResponse = SimpleHttpClientUtil.doGet(apiConfig.getApiNetease(),queryParamBuilder.toString(),
                    null, headersMap);
            if (!Objects.equals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK)) {
                throw new CommonException(ApiMsgEnum.STOCK_QUERY_ERROR);
            }
            String stockData = EntityUtils.toString(httpResponse.getEntity(),  StandardCharsets.UTF_8);
            stockData = stockData.substring(stockData.indexOf("(") + 1, stockData.indexOf(")"));
            JSONObject stockDataJson = JSONUtil.parseObj(stockData);
            List<StockSourceEntity> resultStockList = new ArrayList<>();
            stockList.stream().forEach(stockSource ->
                    resultStockList.add(createStockFromNetease(stockDataJson.getStr(
                                    getQueryParamForNetease(stockSource.getMarketType(), stockSource.getStockCode())),
                            stockSource.getMarketType(), stockSource.getStockCode(), stockSource.getId()))
            );
            return resultStockList;
        } catch (IOException e) {
            log.error("{}", e.getMessage());
            throw new CommonException(ApiMsgEnum.UNKNOWN_ERROR);
        } catch (ArrayIndexOutOfBoundsException e2) {
            throw new CommonException(ApiMsgEnum.STOCK_QUERY_ERROR);
        }
    }

    /**
     * 根据新浪接口返回数据创建股票对象
     *
     * @param stockData 股票数据
     * @param marketType 市场类型
     * @param stockCode 股票编码
     * @return
     */
    private static StockSourceEntity createStockFromSina(String stockData, int marketType, String stockCode){
        return createStockFromSina(stockData, marketType, stockCode, null);
    }

    /**
     * 根据新浪接口返回数据创建股票对象
     *
     * @param stockData 股票数据
     * @param marketType 市场类型
     * @param stockCode 股票编码
     * @param id 股票 id
     * @return
     */
    private static StockSourceEntity createStockFromSina(String stockData, int marketType, String stockCode, Long id) {
        String stockInfo = stockData;
        stockInfo = stockInfo.substring(stockInfo.indexOf("\"") + 1);
        stockInfo = stockInfo.substring(0,stockInfo.lastIndexOf("\""));
        String[] stockDataArr = stockInfo.split(",");
        StockSourceEntity stockSource = new StockSourceEntity();
        stockSource.setId(id);
        stockSource.setMarketType(marketType);
        stockSource.setStockCode(stockCode);
        stockSource.setCompanyName(stockDataArr[0]);
        stockSource.setTodayStartPrice(new BigDecimal(stockDataArr[1]));
        stockSource.setYesterdayEndPrice(new BigDecimal(stockDataArr[2]));
        stockSource.setCurrentPrice(new BigDecimal(stockDataArr[3]));
        stockSource.setTodayMaxPrice(new BigDecimal(stockDataArr[4]));
        stockSource.setTodayMinPrice(new BigDecimal(stockDataArr[5]));
        BigDecimal increase = new BigDecimal(stockDataArr[3]).subtract(new BigDecimal(stockDataArr[2]));
        stockSource.setIncrease(increase);
        if (increase.compareTo(BigDecimal.ZERO) == 0) {
            stockSource.setIncreasePer(BigDecimal.ZERO);
        } else {
            stockSource.setIncreasePer(increase.divide(stockSource.getYesterdayEndPrice(), 4,
                            RoundingMode.HALF_DOWN).multiply(BigDecimal.valueOf(100)));
        }
        stockSource.setTradeNumber(Long.parseLong(stockDataArr[8]));
        stockSource.setTradeAmount(new BigDecimal(stockDataArr[9]));
        stockSource.setDate(stockDataArr[30]);
        stockSource.setTime(stockDataArr[31]);
        return stockSource;
    }

    /**
     * 根据腾讯接口返回数据创建股票对象
     *
     * @param stockData 股票数据
     * @param marketType 市场类型
     * @param stockCode 股票编码
     * @return
     */
    private static StockSourceEntity createStockFromTencent(String stockData, int marketType, String stockCode) {
        return createStockFromTencent(stockData, marketType, stockCode, null);
    }

    /**
     * 根据腾讯接口返回数据创建股票对象
     *
     * @param stockData 股票数据
     * @param marketType 市场类型
     * @param stockCode 股票编码
     * @param id 股票 id
     * @return
     */
    private static StockSourceEntity createStockFromTencent(String stockData, int marketType,
                                                           String stockCode, Long id) {
        String stockInfo = stockData;
        stockInfo = stockInfo.substring(stockInfo.indexOf("\"") + 1);
        stockInfo = stockInfo.substring(0,stockInfo.lastIndexOf("\""));
        String[] stockDataArr = stockInfo.split("~");
        StockSourceEntity stockSource = new StockSourceEntity();
        stockSource.setId(id);
        stockSource.setMarketType(marketType);
        stockSource.setStockCode(stockCode);
        stockSource.setCompanyName(stockDataArr[1]);
        stockSource.setTodayStartPrice(new BigDecimal(stockDataArr[5]));
        stockSource.setYesterdayEndPrice(new BigDecimal(stockDataArr[4]));
        stockSource.setCurrentPrice(new BigDecimal(stockDataArr[3]));
        stockSource.setTodayMaxPrice(new BigDecimal(stockDataArr[33]));
        stockSource.setTodayMinPrice(new BigDecimal(stockDataArr[34]));
        BigDecimal increase = new BigDecimal(stockDataArr[3]).subtract(new BigDecimal(stockDataArr[4]));
        stockSource.setIncrease(increase);
        stockSource.setIncreasePer(new BigDecimal(stockDataArr[32]));
        stockSource.setTradeNumber(Long.parseLong(stockDataArr[36]) * 100);
        stockSource.setTradeAmount(new BigDecimal(stockDataArr[37]).multiply(new BigDecimal(10000)));
        stockSource.setDate(stockDataArr[30].substring(0,8));
        stockSource.setTime(stockDataArr[30].substring(8));
        return stockSource;
    }

    /**
     * 根据网易接口返回数据创建股票对象
     *
     * @param stockData 股票数据
     * @param marketType 市场类型
     * @param stockCode 股票编码
     * @return
     */
    private static StockSourceEntity createStockFromNetease(String stockData, int marketType, String stockCode) {
        return createStockFromNetease(stockData, marketType, stockCode, null);
    }

    /**
     * 根据网易接口返回数据创建股票对象
     *
     * @param stockData 股票数据
     * @param marketType 市场类型
     * @param stockCode 股票编码
     * @param id 股票 id
     * @return
     */
    private static StockSourceEntity createStockFromNetease(String stockData, int marketType,
                                                            String stockCode, Long id) {
        JSONObject jsonObject = JSONUtil.parseObj(stockData);
        StockSourceEntity stockSource = new StockSourceEntity();
        stockSource.setId(id);
        stockSource.setMarketType(marketType);
        stockSource.setStockCode(stockCode);
        stockSource.setCompanyName(jsonObject.getByPath( "name", String.class));
        stockSource.setTodayStartPrice(jsonObject.getByPath("open", BigDecimal.class));
        stockSource.setYesterdayEndPrice(jsonObject.getByPath("yestclose", BigDecimal.class));
        stockSource.setCurrentPrice(jsonObject.getByPath("price", BigDecimal.class));
        stockSource.setTodayMaxPrice(jsonObject.getByPath("high", BigDecimal.class));
        stockSource.setTodayMinPrice(jsonObject.getByPath("low", BigDecimal.class));
        stockSource.setIncrease(jsonObject.getByPath("updown", BigDecimal.class));
        stockSource.setIncreasePer(jsonObject.getByPath("percent", BigDecimal.class)
                .multiply(new BigDecimal(100)));
        stockSource.setTradeNumber(jsonObject.getByPath("volume", Long.class));
        stockSource.setTradeAmount(jsonObject.getByPath("turnover", BigDecimal.class));
        String time = jsonObject.getByPath("time", String.class);
        stockSource.setDate(time.substring(0,time.indexOf(" ") + 1));
        stockSource.setTime(time.substring(time.indexOf(" ")));
        return stockSource;
    }

    /**
     * 设置股票证券市场编码
     *
     * @param stock
     */
    private static void setMarketType(StockSourceEntity stock) {
        MarketEnum marketEnum = MarketEnum.getMarketByType(stock.getMarketType());
        stock.setMarketTypeCode(marketEnum.getCode());
    }

    /**
     * 获取查询参数(新浪、腾讯)
     *
     * @param marketTypeCode
     * @param stockCode
     * @return
     */
    private static String getQueryParam(String marketTypeCode, String stockCode) {
        return marketTypeCode + stockCode;
    }

    /**
     * 获取网易接口请求参数
     *
     * @param marketType
     * @return
     */
    private static String getQueryParamForNetease(int marketType, String stockCode) {
        switch (marketType) {
            case StockConst.MARKET_TYPE_SHANGHAI:
                return "0" + stockCode;
            case StockConst.MARKET_TYPE_SHENZHEN:
                return "1" + stockCode;
            case StockConst.MARKET_TYPE_HK:
                return "2" + stockCode;
            case StockConst.MARKET_TYPE_USA:
                return "3" + stockCode;
            default:
                return "-1";
        }
    }



}
