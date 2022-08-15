package com.ljq.stock.alert.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ljq.stock.alert.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Description: 管理员用户实体类
 * @Author: junqiang.lu
 * @Date: 2022/8/15
 */
@Data
@ToString(callSuper = true)
@TableName(value = "ADMIN_USER", resultMap = "adminUserMap")
@ApiModel(value = "管理员用户实体类", description = "管理员用户实体类")
public class AdminUserEntity extends BaseEntity {

    private static final long serialVersionUID = 5034343400703974147L;

    /**
     * 账号
     * */
    @TableField(value = "ACCOUNT")
    @ApiModelProperty(value = "账号", name = "account")
    private String account;
    /**
     * 密码
     * */
    @TableField(value = "PASSCODE")
    @ApiModelProperty(value = "密码", name = "passcode")
    private String passcode;
    /**
     * 是否启用,0-禁用,1-启用
     * */
    @TableField(value = "ENABLE")
    @ApiModelProperty(value = "是否启用,0-禁用,1-启用", name = "enable")
    private Integer enable;
    /**
     * 昵称
     * */
    @TableField(value = "NICK_NAME")
    @ApiModelProperty(value = "昵称", name = "nickName")
    private String nickName;
    /**
     * 头像链接
     * */
    @TableField(value = "HEAD_URL")
    @ApiModelProperty(value = "头像链接", name = "headUrl")
    private String headUrl;
    /**
     * 手机号
     * */
    @TableField(value = "MOBILE_PHONE")
    @ApiModelProperty(value = "手机号", name = "mobilePhone")
    private String mobilePhone;
    /**
     * 邮箱
     * */
    @TableField(value = "EMAIL")
    @ApiModelProperty(value = "邮箱", name = "email")
    private String email;

    /**
     * 用户 token
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "用户 token", name = "token")
    private String token;

}
