package com.ljq.stock.alert.schedule;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author junqiang.lu
 */
@Slf4j
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.ljq.stock.alert"})
@MapperScan(basePackages = {"com.ljq.stock.alert.dao"})
public class StockAlertScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockAlertScheduleApplication.class, args);
        log.info("---------------[股票预警系统定时任务]启动成功---------------");
    }

}
