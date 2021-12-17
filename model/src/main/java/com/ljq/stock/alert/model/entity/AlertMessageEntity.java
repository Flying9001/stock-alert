package com.ljq.stock.alert.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
    @TableField(value = "USER_ID")
    @ApiModelProperty(value = "用户信息", name = "userId")
    private Long userId;
    /**
     * 手机号
     **/
    @TableField(exist = false)
    @ApiModelProperty(value = "手机号", name = "mobilePhone")
    private String mobilePhone;
    /**
     * 手机发送,1-发送成功,2-发送失败,3-未发送
     **/
    @TableField(value = "PHONE_SEND")
    @ApiModelProperty(value = "手机发送,1-发送成功,2-发送失败,3-未发送", name = "phoneSend")
    private Integer phoneSend;
    /**
     * 邮箱
     **/
    @TableField(exist = false)
    @ApiModelProperty(value = "邮箱", name = "email")
    private String email;
    /**
     * 邮箱发送,1-发送成功,2-发送失败,3-未发送
     **/
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
     * 发件人邮箱
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "发件人邮箱", name = "senderEmail")
    private String senderEmail;

}
