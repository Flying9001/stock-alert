package com.ljq.stock.alert.model.param.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @Description: 用户注册
 * @Author: junqiang.lu
 * @Date: 2021/4/1
 */
@Data
@ToString(callSuper = true)
@ApiModel(value = "用户注册", description = "用户注册")
public class UserRegisterParam extends UserInfoSaveParam {

    private static final long serialVersionUID = -7579532557525811780L;

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
