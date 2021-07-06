package com.ljq.stock.alert.model.param.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * @Description: 获取验证码
 * @Author: junqiang.lu
 * @Date: 2021/5/21
 */
@Data
@ApiModel(value = "获取验证码", description = "获取验证码")
public class UserGetCheckCodeParam implements Serializable {

    private static final long serialVersionUID = 6693054123521392058L;

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
    @ApiModelProperty(value = "邮箱", name = "email", required = true)
    private String email;
    /**
     * 验证码类型
     */
    @NotNull(message = "请选择验证码类型")
    @Min(value = 1, message = "验证码类型选择错误")
    @Max(value = 3, message = "验证码类型选择错误")
    @ApiModelProperty(value = "验证码类型,1-注册,2-登录,3-修改密码", name = "checkCodeType", required = true)
    private Integer checkCodeType;


}
