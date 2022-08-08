package com.ljq.stock.alert.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ljq.stock.alert.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Description: 用户第三方登录信息实体类
 * @Author: junqiang.lu
 * @Date: 2022/8/5
 */
@Data
@ToString(callSuper = true)
@TableName(value = "USER_OAUTH", resultMap = "userOauthMap")
@ApiModel(value = "用户第三方登录信息实体类", description = "用户第三方登录信息实体类")
public class UserOauthEntity extends BaseEntity {

    private static final long serialVersionUID = 2452421726284835336L;


    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    @TableField(value="USER_ID")
    private Long userId;

    /**
     * 第三方接入id
     */
    @ApiModelProperty("第三方接入id")
    @TableField(value="ACCESS_ID")
    private String accessId;

    /**
     * 第三方登录类型
     */
    @ApiModelProperty("第三方登录类型")
    @TableField(value="LOGIN_TYPE")
    private String loginType;

    /**
     * 是否启用，0-不启用，1-启用
     */
    @ApiModelProperty("是否启用，0-不启用，1-启用")
    @TableField(value="ENABLE")
    private Integer enable;

}
