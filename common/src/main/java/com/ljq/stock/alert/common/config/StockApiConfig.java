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
     * 接口地址
     */
    @Value(value = "${stockApi.apiAddress: 127.0.0.1}")
    private String apiAddress;


}
