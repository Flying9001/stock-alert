package com.ljq.stock.alert.model.param.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Description: 用户微信小程序登录
 * @Author: junqiang.lu
 * @Date: 2022/8/2
 */
@Data
public class UserLoginByWechatMiniParam implements Serializable {

    private static final long serialVersionUID = -4506180429624614552L;

    /**
     * 登录凭证代码
     */
    @NotBlank(message = "登录凭证不能为空")
    private String code;

}
