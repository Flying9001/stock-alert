package com.ljq.stock.alert.model.param.user;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description: 用户绑定微信小程序
 * @Author: junqiang.lu
 * @Date: 2022/8/5
 */
@Data
@ApiModel(value = "用户绑定微信小程序", description = "用户绑定微信小程序")
public class UserBindWechatMiniParam implements Serializable {

    private static final long serialVersionUID = -3034321598820367226L;

    /**
     * 用户 id
     */
    @NotNull(message = "用户账号信息不能为空")
    private Long userId;

    /**
     * 登录凭证代码
     */
    @NotBlank(message = "登录凭证不能为空")
    private String code;

}
