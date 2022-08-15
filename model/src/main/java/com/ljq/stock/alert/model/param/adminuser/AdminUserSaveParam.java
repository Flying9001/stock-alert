package com.ljq.stock.alert.model.param.adminuser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 管理员用户参数接收类
 *
 * @author junqiang.lu
 * @date 2022-08-15 11:08:13
 */
@Data
@ApiModel(value = "管理员用户保存(单条)", description = "管理员用户保存(单条)")
public class AdminUserSaveParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 密码
     * */
    @NotBlank(message = "密码 不能为空")
    @Pattern(regexp = "^[\\S]*$", message = "密码不能包含空格、换行等")
    @Length(min = 8, max = 32, message = "密码需要控制在 8-32 位字符,建议使用字母、数字、符号组合")
    @ApiModelProperty(value = "密码", name = "passcode", required = true)
    private String passcode;

    /**
     * 昵称
     * */
    @NotNull(message = "昵称 不能为空")
    @Length(max = 64, message = "昵称不能超过 64 字符")
    @ApiModelProperty(value = "昵称", name = "nickName", required = true)
    private String nickName;

    /**
     * 手机号
     * */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    @ApiModelProperty(value = "手机号", name = "mobilePhone")
    private String mobilePhone;

    /**
     * 邮箱
     * */
    @NotBlank(message = "邮箱 不能为空")
    @Email(message = "邮箱格式错误")
    @Length(max = 64, message = "邮箱长度不超过 64 字符")
    @ApiModelProperty(value = "邮箱", name = "email", required = true)
    private String email;


}
