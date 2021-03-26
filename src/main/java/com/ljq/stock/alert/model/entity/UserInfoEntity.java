package com.ljq.stock.alert.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ljq.stock.alert.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户信息实体类
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Data
@TableName(value = "user_info", resultMap = "userInfoMap")
@ApiModel(value = "用户信息", description = "用户信息")
public class UserInfoEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

    /**
     * 账户
     **/
    @TableField(value = "ACCOUNT")
    @ApiModelProperty(value = "账户", name = "account")
    private String account;
    /**
     * 昵称
     **/
    @TableField(value = "NICK_NAME")
    @ApiModelProperty(value = "昵称", name = "nickName")
    private String nickName;
    /**
     * 密码
     **/
    @TableField(value = "PASSCODE")
    @ApiModelProperty(value = "密码", name = "passcode")
    private String passcode;
    /**
     * 手机号
     **/
    @TableField(value = "MOBILE_PHONE")
    @ApiModelProperty(value = "手机号", name = "mobilePhone")
    private String mobilePhone;
    /**
     * 邮箱
     **/
    @TableField(value = "EMAIL")
    @ApiModelProperty(value = "邮箱", name = "email")
    private String email;

}
