package com.ljq.stock.alert.model.param.adminuser;

import com.ljq.stock.alert.model.BasePageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

/**
 * 管理员用户参数接收类
 *
 * @author junqiang.lu
 * @date 2022-08-15 11:08:13
 */
@Data
@ApiModel(value = "管理员用户查询列表", description = "管理员用户查询列表")
public class AdminUserListParam extends BasePageParam {

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
    @Length(max = 64, message = "昵称不能超过 64 字符")
    @ApiModelProperty(value = "昵称", name = "nickName")
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
    @Email(message = "邮箱格式错误")
    @Length(max = 64, message = "邮箱长度不超过 64 字符")
    @ApiModelProperty(value = "邮箱", name = "email")
    private String email;



}
