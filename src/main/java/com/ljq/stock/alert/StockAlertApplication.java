package com.ljq.stock.alert;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author junqiang.lu
 */
@SpringBootApplication(scanBasePackages = {"com.ljq.stock.alert"})
@MapperScan(basePackages = {"com.ljq.stock.alert.dao"})
public class StockAlertApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockAlertApplication.class, args);
        System.out.println("---------------[股票预警系统]启动成功---------------");
    }

}
