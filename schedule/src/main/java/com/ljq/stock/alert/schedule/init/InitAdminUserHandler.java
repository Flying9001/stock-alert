package com.ljq.stock.alert.schedule.init;

import cn.hutool.core.util.RandomUtil;
import com.ljq.stock.alert.common.util.Md5Util;
import com.ljq.stock.alert.model.entity.AdminUserEntity;
import com.ljq.stock.alert.service.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description: 初始化管理员用户
 * @Author: junqiang.lu
 * @Date: 2023/11/27
 */
@Slf4j
@Component
public class InitAdminUserHandler implements ApplicationRunner {

    @Resource
    private AdminUserService adminUserService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 校验是否存在管理员账号
        int count = adminUserService.count();
        if (count > 0) {
            return;
        }
        AdminUserEntity adminUser = new AdminUserEntity();
        adminUser.setAccount("admin" + RandomUtil.randomNumbers(4));
        String password = RandomUtil.randomString(10);
        // 密码加密
        try {
            adminUser.setPasscode(Md5Util.getEncryptedPwd(password));
        } catch (Exception e) {
            log.error("密码加密失败", e);
        }
        // 保存
        adminUserService.save(adminUser);
        log.info("init-初始化管理员账号-account:{},passcode:{}", adminUser.getAccount(), password);
    }
}
