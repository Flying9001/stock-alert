package com.ljq.stock.alert.common.component;

import cn.hutool.core.util.RandomUtil;
import com.ljq.stock.alert.web.StockAlertApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.Date;

@SpringBootTest(classes = {StockAlertApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = {"classpath:application.yml", "classpath:application-dev.yml"})
class MailClientTest {

    @Resource
    private MailClient mailClient;

    private String receiveEmail = "xxxxxxxxxx@gmail.com";

    @Test
    void sendMail() throws MessagingException {

        for (int i = 0; i < 5; i++) {
            String title = "邮件测试" + new Date();
            String content = "啊哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈" + RandomUtil.randomString(50);
            mailClient.sendMail(receiveEmail, title, content);
        }

    }
}