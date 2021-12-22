package com.ljq.stock.alert.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 用户 Token 对象
 * @Author: junqiang.lu
 * @Date: 2021/6/15
 */
@Data
@ApiModel(value = "用户 Token 对象", description = "用户 Token 对象")
public class UserTokenVo implements Serializable {

    private static final long serialVersionUID = 8686083446544822320L;

    /**
     * 用户 id
     */
    @ApiModelProperty(value = "用户 id", name = "id")
    private Long id;
    /**
     * 账户
     **/
    @ApiModelProperty(value = "账户", name = "account")
    private String account;
    /**
     * 手机号
     **/
    @ApiModelProperty(value = "手机号", name = "mobilePhone")
    private String mobilePhone;
    /**
     * 邮箱
     **/
    @ApiModelProperty(value = "邮箱", name = "email")
    private String email;
    /**
     * Token 时间
     */
    @ApiModelProperty(value = "用户 Token 时间", name = "tokenTime")
    private Long tokenTime;

}
