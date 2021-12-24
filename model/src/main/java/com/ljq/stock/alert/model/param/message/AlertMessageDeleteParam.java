package com.ljq.stock.alert.model.param.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 预警消息删除(单条)
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Data
@ToString(callSuper = true)
@ApiModel(value = "预警消息删除(单条)", description = "预警消息删除(单条)")
public class AlertMessageDeleteParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     **/
    @NotNull(message = "请选择需要删除的对象")
    @Min(value = 1, message = "请求参数不合理")
    @ApiModelProperty(value = "id", name = "id", required = true)
    private Long id;
    /**
     * 用户 id
     * */
    @Min(value = 1, message = "用户 id 至少为 1")
    @ApiModelProperty(value = "用户 id 不能为空,至少为 1", name = "userId", example = "0")
    private Long userId;

}
