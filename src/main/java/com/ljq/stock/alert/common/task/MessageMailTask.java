package com.ljq.stock.alert.common.task;

import com.ljq.stock.alert.model.entity.AlertMessageEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.util.concurrent.Callable;

/**
 * @Description: 预警消息邮件发送任务
 * @Author: junqiang.lu
 * @Date: 2021/5/13
 */

public class MessageMailTask implements Callable<AlertMessageEntity> {

    private JavaMailSender mailSender;

    private AlertMessageEntity alertMessage;

    public MessageMailTask(JavaMailSender mailSender, AlertMessageEntity alertMessage) {
        this.mailSender = mailSender;
        this.alertMessage = alertMessage;
    }

    @Override
    public AlertMessageEntity call() throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom(alertMessage.getSenderEmail());
        helper.setTo(alertMessage.getEmail());
        helper.setSubject(alertMessage.getTitle());
        helper.setText(alertMessage.getContent(), true);
        mailSender.send(helper.getMimeMessage());
        return alertMessage;
    }
}
