package com.ljq.stock.alert.model.param.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @Description: 用户修改邮箱请求参数
 * @Author: junqiang.lu
 * @Date: 2022/5/18
 */
@Data
@ApiModel(value = "用户修改邮箱", description = "用户修改邮箱")
public class UserUpdateEmailParam implements Serializable {

    private static final long serialVersionUID = -4090736184185061819L;

    /**
     * 新邮箱
     * */
    @NotBlank(message = "邮箱 不能为空")
    @Email(message = "邮箱格式错误")
    @ApiModelProperty(value = "新邮箱", name = "email", required = true)
    private String email;

    /**
     * 验证码
     */
    @NotBlank(message = "请输入验证码")
    @Pattern(regexp = "^\\d{6}$", message = "验证码格式错误")
    @ApiModelProperty(value = "验证码", name = "checkCode", required = true)
    private String checkCode;

}
