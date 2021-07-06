package com.ljq.stock.alert.service.util;

import com.ljq.stock.alert.model.vo.UserTokenVo;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 上下文会话工具
 * @Author: junqiang.lu
 * @Date: 2021/6/15
 */
@Setter
@Getter
public class SessionUtil {

    private static ThreadLocal<SessionUtil> context = ThreadLocal.withInitial(() -> new SessionUtil());

    private UserTokenVo userToken;

    /**
     * 获取当前上下文会话
     *
     * @return
     */
    public static SessionUtil currentSession() {
        return context.get();
    }

    /**
     * 移除当前会话中内容
     */
    public static void remove() {
        context.remove();
    }


}
