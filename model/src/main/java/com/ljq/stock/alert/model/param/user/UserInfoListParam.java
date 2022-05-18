package com.ljq.stock.alert.model.param.user;

import com.ljq.stock.alert.model.BasePageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

/**
 * 用户信息分页查询
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Data
@ToString(callSuper = true)
@ApiModel(value = "用户信息分页查询", description = "用户信息分页查询")
public class UserInfoListParam extends BasePageParam {

    private static final long serialVersionUID = 1L;

    /**
     * id
     * */
    @Min(value = 1, message = "id 至少为 1")
    @ApiModelProperty(value = "id 不能为空,至少为 1", name = "id", example = "0")
    private Long id;
    /**
     * 昵称
     * */
    @Length(max = 16,message = "昵称需要控制在 16 字符以内")
    @ApiModelProperty(value = "昵称", name = "nickName")
    private String nickName;
    /**
     * 手机号
     * */
    @Pattern(regexp = "^(1[3-9]\\d{9})?$", message = "手机号格式错误")
    @ApiModelProperty(value = "手机号", name = "mobilePhone")
    private String mobilePhone;
    /**
     * 邮箱
     * */
    @Email(message = "邮箱格式错误")
    @ApiModelProperty(value = "邮箱", name = "email")
    private String email;




}
