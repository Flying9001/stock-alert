package com.ljq.stock.alert.service.component;

import com.ljq.stock.alert.model.entity.AlertMessageEntity;
import com.ljq.stock.alert.service.AlertMessageService;
import com.ljq.stock.alert.web.StockAlertApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;

@Slf4j
@SpringBootTest(classes = {StockAlertApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = {"classpath:application.yml", "classpath:application-dev.yml"})
class AlertMessageMqSenderTest {

    @Resource
    private AlertMessageService alertMessageService;
    @Resource
    private AlertMessageMqSender alertMessageMqSender;

    @Test
    void sendBatchAlertMessage() {
        // 创建预警消息
        AlertMessageEntity message = new AlertMessageEntity();
        message.setUserId(1L);
        message.setStockId(1L);
        message.setPushTotal(0);
        message.setPushCount(0);
        message.setAlertType(21);
        message.setTitle("股票测试" + new Date());
        message.setContent("这里是股票预警提醒test数据");
        alertMessageService.save(message);
        alertMessageMqSender.sendBatchAlertMessage(Collections.singletonList(message));


    }
}