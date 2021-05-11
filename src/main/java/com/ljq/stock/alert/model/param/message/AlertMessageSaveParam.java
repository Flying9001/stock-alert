package com.ljq.stock.alert.model.param.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 预警消息新增(单条)
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Data
@ApiModel(value = "预警消息新增(单条)", description = "预警消息新增(单条)")
public class AlertMessageSaveParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户信息
     * */
    @NotNull(message = "请选择接收通知的用户")
    @Min(value = 1, message = "用户 ID 至少为 1")
    @ApiModelProperty(value = "用户信息 不能为空,至少为 1", name = "userId", required = true, example = "0")
    private Long userId;
    /**
     * 股票 id
     * */
    @NotNull(message = "股票 id 不能为空")
    @Min(value = 1, message = "股票 id 至少为 1")
    @ApiModelProperty(value = "股票 id 不能为空,至少为 1", name = "stockId", required = true, example = "0")
    private Long stockId;


}
