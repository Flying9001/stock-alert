package com.ljq.stock.alert.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ljq.stock.alert.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
/**
 * @description 用户消息推送方式
 * @author junqiang.lu
 * @date 2023-07-31
 */
@Data
@ToString(callSuper = true)
@TableName(value = "user_push_type")
@ApiModel(value = "用户消息推送方式", description = "用户消息推送方式")
public class UserPushTypeEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id", example = "0")
    private Long userId;

    /**
     * 推送方式，1-短信;2-邮件;3-pushplus
     */
    @ApiModelProperty(value = "推送方式，1-短信;2-邮件;3-pushplus", example = "0")
    private Integer pushType;

    /**
     * 通知推送接收地址
     */
    @ApiModelProperty(value = "通知推送接收地址")
    private String receiveAddress;

    /**
     * 是否启用，0-未启用，1-启用
     */
    @ApiModelProperty(value = "是否启用，0-未启用，1-启用", example = "0")
    private Integer enable;

}