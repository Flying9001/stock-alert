package com.ljq.stock.alert.model.param.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 用户信息新增(单条)
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Data
@ApiModel(value = "用户信息新增(单条)", description = "用户信息新增(单条)")
public class UserInfoSaveParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 昵称
     * */
    @Length(max = 16,message = "昵称需要控制在 16 字符以内")
    @ApiModelProperty(value = "昵称", name = "nickName", required = true)
    private String nickName;
    /**
     * 密码
     * */
    @Pattern(regexp = "^[\\S]{8,32}$", message = "密码不能包含空格、换行等")
    @Length(min = 8, max = 32, message = "密码需要控制在 8-32 位字符,建议使用字母、数字、符号组合")
    @ApiModelProperty(value = "密码", name = "passcode", required = true)
    private String passcode;
    /**
     * 手机号
     * */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    @ApiModelProperty(value = "手机号", name = "mobilePhone", required = true)
    private String mobilePhone;
    /**
     * 邮箱
     * */
    @NotBlank(message = "邮箱 不能为空")
    @Email(message = "邮箱格式错误")
    @ApiModelProperty(value = "邮箱", name = "email", required = true)
    private String email;


}
