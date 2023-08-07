package com.ljq.stock.alert.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ljq.stock.alert.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 消息推送结果实体类
 *
 * @author junqiang.lu
 * @date 2023-08-04 17:50:24
 */
@Data
@ToString(callSuper = true)
@TableName(value = "MESSAGE_PUSH_RESULT")
@ApiModel(value = "消息推送结果", description = "消息推送结果")
public class MessagePushResultEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

    /**
	 * 消息id
	 * */
	@ApiModelProperty(value = "消息id", name = "messageId")
	private Long messageId;
    /**
	 * 推送类型,1-短信,2-邮箱,3-pushplus
	 * */
	@ApiModelProperty(value = "推送类型,1-短信,2-邮箱,3-pushplus", name = "pushType")
	private Integer pushType;
    /**
	 * 推送结果,0-失败,1-成功,2-未推送
	 * */
	@ApiModelProperty(value = "推送结果,0-失败,1-成功,2-未推送", name = "pushResult")
	private Integer pushResult;
    /**
	 * 消息发送失败重试次数
	 * */
	@ApiModelProperty(value = "消息发送失败重试次数", name = "retryTime")
	private Integer retryTime;

}
