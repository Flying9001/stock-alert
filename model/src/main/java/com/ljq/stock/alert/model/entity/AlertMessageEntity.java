package com.ljq.stock.alert.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ljq.stock.alert.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 预警消息实体类
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Data
@ToString(callSuper = true)
@TableName(value = "ALERT_MESSAGE", resultMap = "alertMessageMap")
@ApiModel(value = "预警消息", description = "预警消息")
public class AlertMessageEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

    /**
     * 用户信息
     **/
    @JsonSerialize(using= ToStringSerializer.class)
    @TableField(value = "USER_ID")
    @ApiModelProperty(value = "用户信息", name = "userId")
    private Long userId;

    @ApiModelProperty(value = "推送类型,1-短信,2-邮箱,3-pushplus")
    private Integer pushType;

    @ApiModelProperty(value = "推送结果,0-失败,1-成功,2-未推送")
    private Integer pushResult;

    /**
     * 手机发送,1-发送成功,2-发送失败,3-未发送
     **/
    @Deprecated
    @TableField(value = "PHONE_SEND")
    @ApiModelProperty(value = "手机发送,1-发送成功,2-发送失败,3-未发送", name = "phoneSend")
    private Integer phoneSend;
    /**
     * 邮箱发送,1-发送成功,2-发送失败,3-未发送
     **/
    @Deprecated
    @TableField(value = "EMAIL_SEND")
    @ApiModelProperty(value = "邮箱发送,1-发送成功,2-发送失败,3-未发送", name = "emailSend")
    private Integer emailSend;
    /**
     * 提醒类型,1-股价提醒;2-单日涨跌幅提醒
     */
    @TableField(value = "ALERT_TYPE")
    @ApiModelProperty(value = "提醒类型,1-股价提醒;2-单日涨跌幅提醒", name = "alertType")
    private Integer alertType;
    /**
     * 消息推送失败重试次数
     */
    @TableField(value = "RETRY_TIME")
    @ApiModelProperty(value = "消息推送失败重试次数", name = "retryTime")
    private Integer retryTime;
    /**
     * 股票 id
     **/
    @TableField(value = "STOCK_ID")
    @ApiModelProperty(value = "股票 id", name = "stockId")
    private Long stockId;
    /**
     * 消息标题
     */
    @TableField(value = "TITLE")
    @ApiModelProperty(value = "消息标题", name = "title")
    private String title;
    /**
     * 消息内容
     **/
    @TableField(value = "CONTENT")
    @ApiModelProperty(value = "消息内容", name = "content")
    private String content;
    /**
     * 手机号
     **/
    @Deprecated
    @TableField(value = "MOBILE_PHONE", exist = false)
    @ApiModelProperty(value = "手机号", name = "mobilePhone")
    private String mobilePhone;
    /**
     * 邮箱
     **/
    @Deprecated
    @TableField(value = "EMAIL",exist = false)
    @ApiModelProperty(value = "邮箱", name = "email")
    private String email;

    @TableField(exist = false)
    @ApiModelProperty(value = "接收通知的地址")
    private String receiveAddress;

    @TableField(exist = false)
    @ApiModelProperty(value = "发送通知的地址")
    private String sendAddress;

    /**
     * 发件人邮箱
     */
    @Deprecated
    @TableField(exist = false)
    @ApiModelProperty(value = "发件人邮箱", name = "senderEmail")
    private String senderEmail;

}
