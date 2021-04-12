package com.ljq.stock.alert.model.param.user;

import com.ljq.stock.alert.model.BasePageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

/**
 * 用户信息分页查询
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Data
@ApiModel(value = "用户信息分页查询", description = "用户信息分页查询")
public class UserInfoListParam extends BasePageParam {

    private static final long serialVersionUID = 1L;

    /**
     * id
     * */
    @Min(value = 1, message = "id 至少为 1")
    @ApiModelProperty(value = "id 不能为空,至少为 1", name = "id", required = true, example = "0")
    private Long id;
    /**
     * 昵称
     * */
    @Length(max = 16,message = "昵称需要控制在 16 字符以内")
    @ApiModelProperty(value = "昵称", name = "nickName", required = true)
    private String nickName;
    /**
     * 手机号
     * */
    @Pattern(regexp = "^(1[3-9]\\d{9})?$", message = "手机号格式错误")
    @ApiModelProperty(value = "手机号", name = "mobilePhone", required = true)
    private String mobilePhone;
    /**
     * 邮箱
     * */
    @Email(message = "邮箱格式错误")
    @ApiModelProperty(value = "邮箱", name = "email", required = true)
    private String email;




}
