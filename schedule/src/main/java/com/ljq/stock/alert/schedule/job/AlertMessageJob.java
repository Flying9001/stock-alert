package com.ljq.stock.alert.schedule.job;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljq.stock.alert.common.component.RedisUtil;
import com.ljq.stock.alert.common.config.StockApiConfig;
import com.ljq.stock.alert.common.constant.StockConst;
import com.ljq.stock.alert.common.util.CacheKeyUtil;
import com.ljq.stock.alert.dao.AlertMessageDao;
import com.ljq.stock.alert.dao.UserStockDao;
import com.ljq.stock.alert.model.entity.AlertMessageEntity;
import com.ljq.stock.alert.model.entity.StockSourceEntity;
import com.ljq.stock.alert.model.entity.UserStockEntity;
import com.ljq.stock.alert.service.AlertMessageService;
import com.ljq.stock.alert.service.component.AlertMessageMqSender;
import com.ljq.stock.alert.service.util.MessageHelper;
import com.ljq.stock.alert.service.util.StockUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Description: 预警消息推送
 * @Author: junqiang.lu
 * @Date: 2021/5/8
 */
@Slf4j
@Component
public class AlertMessageJob {

    @Autowired
    private AlertMessageService alertMessageService;
    @Autowired
    private AlertMessageDao alertMessageDao;
    @Autowired
    private StockApiConfig stockApiConfig;
    @Autowired
    private UserStockDao userStockDao;
    @Autowired
    private AlertMessageMqSender alertMessageMqSender;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 刷新股票数据
     * 5 秒 1 次
     */
    @Scheduled(fixedDelay = 5 * 1000L, initialDelay = 5 * 1000L)
    public void flashStockData() {
        // 从缓存中读取所有股票数据
        List<StockSourceEntity> stockCacheList = redisUtil.mapGetAll(StockConst.CACHE_KEY_STOCK_SOURCE_ALL,
                StockSourceEntity.class);
        if (CollUtil.isEmpty(stockCacheList)) {
            return;
        }
        // 查询股票数据
        List<StockSourceEntity> stockLiveList = StockUtil.getStocksFromSina(stockApiConfig, stockCacheList);
        // 更新缓存中股票数据
        Map<String, Object> stockSourceMap = new HashMap<>(16);
        stockLiveList.stream().forEach(stockSource ->
                stockSourceMap.put(CacheKeyUtil.createStockSourceKey(stockSource.getMarketType(),
                        stockSource.getStockCode()), stockSource));
        redisUtil.mapPutBatch(StockConst.CACHE_KEY_STOCK_SOURCE_ALL, stockSourceMap);
    }

    /**
     * 比对股票数据
     * 10 秒 1 次
     */
    @Scheduled(fixedDelay = 10 * 1000L, initialDelay = 30 * 1000L)
    public void compareStockPrice() {
        // 查询所有用户关注的股票
        int countAll = userStockDao.selectCount(Wrappers.emptyWrapper());
        int pageSize = 1000;
        int times = countAll % pageSize == 0 ? countAll / pageSize : (countAll / pageSize) + 1;
        IPage<UserStockEntity> page = new Page<>(1,pageSize);
        for (int i = 1; i < times + 1; i++) {
           page.setCurrent(i);
           page = userStockDao.queryPage(Collections.emptyMap(), page);
            // 创建预警消息
            if (CollUtil.isEmpty(page.getRecords())) {
                continue;
            }
            // 获取用户关注股票的实时价格
            page.getRecords().stream().forEach(userStock ->
                userStock.setStockSource(redisUtil.mapGet(StockConst.CACHE_KEY_STOCK_SOURCE_ALL,
                        CacheKeyUtil.createStockSourceKey(userStock.getStockSource().getMarketType(),
                                userStock.getStockSource().getStockCode()), StockSourceEntity.class)));
            createAndSendMessageBatch(page.getRecords());
        }

    }

    /**
     * 消息重试,最多重试 5 次
     * 120 秒 1 次
     */
    @Scheduled(fixedDelay = 120 * 1000L, initialDelay = 30 * 1000L)
    public void messageReTry() {
        // 统计所有当天发送失败的消息
        int pageSize = 1000;
        IPage<AlertMessageEntity> pageParam = new Page<>(1, pageSize);
        IPage<AlertMessageEntity> pageResult = alertMessageDao.queryPageFailMessage(pageParam);
        if (pageResult.getTotal() < 1) {
            return;
        }
        long countAll = pageResult.getTotal();
        long times = countAll % pageSize == 0 ? countAll / pageSize : (countAll / pageSize) + 1;
        alertMessageMqSender.sendBatchAlertMessage(pageResult.getRecords());
        for (int i = 2; i < times + 1; i++) {
            pageParam.setCurrent(i);
            pageResult = alertMessageDao.queryPageFailMessage(pageParam);
            if (CollUtil.isEmpty(pageResult.getRecords())) {
                continue;
            }
            alertMessageMqSender.sendBatchAlertMessage(pageResult.getRecords());
        }
    }

    /**
     * 批量创建并发送消息
     *
     * @param userStockList
     */
    public void createAndSendMessageBatch(List<UserStockEntity> userStockList) {
        List<AlertMessageEntity> alertMessageList = MessageHelper.createAlertMessageBatch(userStockList);
        if (CollUtil.isEmpty(alertMessageList)) {
            return;
        }
        // 消息去重
        int countRepeat = 0;
        List<AlertMessageEntity> messageSendList = new ArrayList<>();
        for (int i = 0; i < alertMessageList.size(); i++) {
            countRepeat = alertMessageDao.validateRepeat(alertMessageList.get(i));
            if (countRepeat < 1) {
                messageSendList.add(alertMessageList.get(i));
            }
        }
        if (CollUtil.isEmpty(messageSendList)) {
            return;
        }
        // 保存预警消息
        alertMessageService.saveBatch(messageSendList);
        // 推送预警消息
        log.info("预警消息推送: {}", messageSendList);
        alertMessageMqSender.sendBatchAlertMessage(messageSendList);
    }




}
