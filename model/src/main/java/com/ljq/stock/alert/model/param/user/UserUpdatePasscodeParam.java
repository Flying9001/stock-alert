package com.ljq.stock.alert.model.param.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @Description: 用户修改密码请求参数
 * @Author: junqiang.lu
 * @Date: 2022/5/16
 */
@Data
@ApiModel(value = "用户修改密码", description = "用户修改密码")
public class UserUpdatePasscodeParam implements Serializable {

    private static final long serialVersionUID = 6103625428193805734L;


    /**
     * 密码
     * */
    @NotBlank(message = "密码 不能为空")
    @Pattern(regexp = "^[\\S]{8,32}$", message = "密码不能包含空格、换行等")
    @Length(min = 8, max = 32, message = "密码需要控制在 8-32 位字符,建议使用字母、数字、符号组合")
    @ApiModelProperty(value = "密码", name = "passcode", required = true)
    private String passcode;

    /**
     * 验证码
     */
    @NotBlank(message = "请输入验证码")
    @Pattern(regexp = "^\\d{6}$", message = "验证码格式错误")
    @ApiModelProperty(value = "验证码", name = "checkCode", required = true)
    private String checkCode;

}
