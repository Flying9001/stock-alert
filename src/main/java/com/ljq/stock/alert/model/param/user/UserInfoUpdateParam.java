package com.ljq.stock.alert.model.param.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息修改(单条)
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Data
@ApiModel(value = "用户信息修改(单条)", description = "用户信息修改(单条)")
public class UserInfoUpdateParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     **/
    @NotNull(message = "id 不能为空")
    @ApiModelProperty(value = "id 不能为空", name = "id")
    private Long id;
    /**
     * 账户
     * */
    @NotNull(message = "账户 不能为空")
    @ApiModelProperty(value = "账户", name = "account", required = true)
    private String account;
    /**
     * 昵称
     * */
    @NotNull(message = "昵称 不能为空")
    @ApiModelProperty(value = "昵称", name = "nickName", required = true)
    private String nickName;
    /**
     * 密码
     * */
    @NotNull(message = "密码 不能为空")
    @ApiModelProperty(value = "密码", name = "passcode", required = true)
    private String passcode;
    /**
     * 手机号
     * */
    @NotNull(message = "手机号 不能为空")
    @ApiModelProperty(value = "手机号", name = "mobilePhone", required = true)
    private String mobilePhone;
    /**
     * 邮箱
     * */
    @NotNull(message = "邮箱 不能为空")
    @ApiModelProperty(value = "邮箱", name = "email", required = true)
    private String email;
    /**
     * 创建时间
     * */
    @NotNull(message = "创建时间 不能为空")
    @ApiModelProperty(value = "创建时间", name = "createDate", required = true)
    private Date createDate;
    /**
     * 更新时间
     * */
    @NotNull(message = "更新时间 不能为空")
    @ApiModelProperty(value = "更新时间", name = "updateTime", required = true)
    private Date updateTime;


}
