package com.ljq.stock.alert.schedule.init;

import com.ljq.stock.alert.common.component.RedisUtil;
import com.ljq.stock.alert.common.constant.StockConst;
import com.ljq.stock.alert.service.StockSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description: 初始化股票数据处理器
 * @Author: junqiang.lu
 * @Date: 2023/11/10
 */
@Slf4j
@Component
public class InitStockSourceHandler implements ApplicationRunner {

    @Resource
    private StockSourceService stockSourceService;
    @Resource
    private RedisUtil redisUtil;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 判断缓存中是否存在股票数据
        boolean existFlag = redisUtil.exists(StockConst.CACHE_KEY_STOCK_SOURCE_ALL);
        if (existFlag) {
            return;
        }
        log.info("init-将所有数据库中的股票添加到缓存");
        // 将所有数据库中的股票添加到缓存
        stockSourceService.allDbToCache();
    }


}
