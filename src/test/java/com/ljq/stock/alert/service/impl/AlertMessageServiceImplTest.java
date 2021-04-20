package com.ljq.stock.alert.service.impl;

import com.ljq.stock.alert.model.param.message.AlertMessageSaveParam;
import com.ljq.stock.alert.service.AlertMessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AlertMessageServiceImplTest {

    @Autowired
    private AlertMessageService messageService;

    @Test
    void save() {
        AlertMessageSaveParam saveParam = new AlertMessageSaveParam();
        saveParam.setUserId(1376508130044678145L);
        saveParam.setEmailSend(3);
        saveParam.setPhoneSend(3);
        saveParam.setStockId(1375732096605167617L);
        saveParam.setTitle("股票预警-TCL科技");
        saveParam.setContent("你好，你关招商银行注的股票[TCL科技(A股000100)]当前股价为[55.56]，达到了股票最高预警值[55]");
        messageService.save(saveParam);

    }
}