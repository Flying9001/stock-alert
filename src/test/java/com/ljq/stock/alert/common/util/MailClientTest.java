package com.ljq.stock.alert.common.util;

import com.ljq.stock.alert.common.component.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;

@SpringBootTest
class MailClientTest {

    @Autowired
    private MailClient mailClient;

    @Test
    void sendMail() throws MessagingException {
        mailClient.sendMail("flying9001@gmail.com","[股票预警]-招商银行",
                "你好，你关注的股票[招商银行(A股345634)]当前股价为[66.56]，达到了股票最高预警值[66]");

    }
}