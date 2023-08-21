package com.ljq.stock.alert.common.component;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
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

class WxPusherClientTest {

    @Resource
    private WxPusherClient wxPusherClient;

    private String receiveAddress = "xxxx";

    @Test
    void createQrCode() {
    }

    @Test
    void push() {
        WxPusherClient.PushParam pushParam = new WxPusherClient.PushParam();
        pushParam.setSummary(RandomUtil.randomString(5) + new Date());
        pushParam.setContentType(1);
        pushParam.setContent(RandomUtil.randomString(50));
        pushParam.setUids(Collections.singletonList(receiveAddress));
        String recordId = wxPusherClient.push(pushParam);
        log.info("recordId is null: {}", StrUtil.isBlank(recordId));
        log.info("recordId: {}", recordId);
    }
}