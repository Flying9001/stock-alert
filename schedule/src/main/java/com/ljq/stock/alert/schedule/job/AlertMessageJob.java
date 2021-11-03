package com.ljq.stock.alert.schedule.job;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljq.stock.alert.common.config.StockApiConfig;
import com.ljq.stock.alert.dao.AlertMessageDao;
import com.ljq.stock.alert.dao.UserStockDao;
import com.ljq.stock.alert.model.entity.AlertMessageEntity;
import com.ljq.stock.alert.model.entity.StockSourceEntity;
import com.ljq.stock.alert.model.entity.UserStockEntity;
import com.ljq.stock.alert.service.AlertMessageService;
import com.ljq.stock.alert.service.StockSourceService;
import com.ljq.stock.alert.service.component.AlertMessageMqSender;
import com.ljq.stock.alert.service.util.MessageHelper;
import com.ljq.stock.alert.service.util.StockUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
    private StockSourceService stockSourceService;
    @Autowired
    private StockApiConfig stockApiConfig;
    @Autowired
    private UserStockDao userStockDao;
    @Autowired
    private AlertMessageMqSender alertMessageMqSender;

    /**
     * 刷新股票数据
     * 1 分钟 1 次
     */
    @Scheduled(fixedDelay = 60 * 1000, initialDelay = 1 * 1000)
    public void flashStockData() {
        // 统计所有股票数量
        int countAll = stockSourceService.count();
        int pageSize = 1000;
        int times = countAll % pageSize == 0 ? countAll / pageSize : (countAll / pageSize) + 1;
        IPage<StockSourceEntity> page = new Page<>(1, pageSize);
        // 分批次更新股票价格
        for (int i = 0; i < times; i++) {
            page.setCurrent(i);
            page = stockSourceService.page(page, Wrappers.emptyWrapper());
            if (CollUtil.isEmpty(page.getRecords())) {
                continue;
            }
            // 批量查询股票数据
            List<StockSourceEntity> stockLiveList = StockUtil.getStocksFromSina(stockApiConfig, page.getRecords());
            // 批量更新股票数据
            stockSourceService.updateBatchById(stockLiveList);
        }
    }

    /**
     * 比对股票数据
     * 1 分钟 1 次
     */
    @Scheduled(fixedDelay = 60 * 1000, initialDelay = 60 * 1000)
    public void compareStockPrice() {
        // 查询所有用户关注的股票
        int countAll = userStockDao.selectCount(Wrappers.emptyWrapper());
        int pageSize = 1000;
        int times = countAll % pageSize == 0 ? countAll / pageSize : (countAll / pageSize) + 1;
        IPage<UserStockEntity> page = new Page<>(1,pageSize);
        for (int i = 1; i < times + 1; i++) {
           page.setCurrent(i);
           page = userStockDao.queryList(null, page);
            // 创建预警消息
            if (CollUtil.isEmpty(page.getRecords())) {
                continue;
            }
            createAndSendMessageBatch(page.getRecords());
        }

    }

    /**
     * 批量创建并发送消息
     *
     * @param userStockList
     */
    public void createAndSendMessageBatch(List<UserStockEntity> userStockList) {
        List<AlertMessageEntity> alertMessageList = MessageHelper.createMessageBatch(userStockList);
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
        alertMessageMqSender.sendBatch(messageSendList);
    }




}
