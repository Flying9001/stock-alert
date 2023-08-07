package com.ljq.stock.alert.common.component;

import cn.hutool.core.util.RandomUtil;
import com.ljq.stock.alert.common.config.PushPlusConfig;
import com.ljq.stock.alert.common.constant.PushPlusChannelEnum;
import com.ljq.stock.alert.web.StockAlertApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.annotation.Resource;
import java.util.Date;


@Slf4j
@SpringBootTest(classes = {StockAlertApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = {"classpath:application.yml", "classpath:application-dev.yml"})
class PushPlusClientTest {

    @Resource
    private PushPlusConfig pushPlusConfig;

    @Resource
    private PushPlusClient pushPlusClient;

    private String token = "xxx";

    @Test
    void push() {
      log.info("pushPlus config: {}", pushPlusConfig);
      PushPlusClient.PushPlusPushParam pushParam = new PushPlusClient.PushPlusPushParam();
      pushParam.setToken(token);
      pushParam.setTitle("plushPlush" + new Date());
      pushParam.setContent(RandomUtil.randomString(50));
      pushParam.setChannel(PushPlusChannelEnum.WECHAT_PUBLIC.getChannel());
      pushPlusClient.push(pushParam);




    }
}