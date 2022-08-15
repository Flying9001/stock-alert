package com.ljq.stock.alert.model.param.adminuser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @Description: 管理员用户登录请求参数
 * @Author: junqiang.lu
 * @Date: 2022/8/15
 */
@Data
@ApiModel(value = "管理员用户登录请求参数", description = "管理员用户登录请求参数")
public class AdminUserLoginParam implements Serializable {

    private static final long serialVersionUID = 5575409246135148244L;


    /**
     * 账户
     * */
    @NotBlank(message = "请输入账号/手机号/邮箱")
    @Length(max = 64, message = "请输入有效账户(账号/手机号/邮箱),长度不超过 64 字符")
    @ApiModelProperty(value = "账户/手机号/邮箱", name = "account", required = true)
    private String account;
    /**
     * 密码
     * */
    @NotBlank(message = "密码 不能为空")
    @Pattern(regexp = "^[\\S]*$", message = "密码不能包含空格、换行等")
    @Length(min = 8, max = 32, message = "密码需要控制在 8-32 位字符,建议使用字母、数字、符号组合")
    @ApiModelProperty(value = "密码", name = "passcode", required = true)
    private String passcode;

}
