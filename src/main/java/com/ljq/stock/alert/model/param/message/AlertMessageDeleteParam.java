package com.ljq.stock.alert.model.param.message;

import com.ljq.stock.alert.model.BaseInfoParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * 预警消息删除(单条)
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Data
@ApiModel(value = "预警消息删除(单条)", description = "预警消息删除(单条)")
public class AlertMessageDeleteParam extends BaseInfoParam {

    private static final long serialVersionUID = 1L;

    /**
     * 用户 id
     * */
    @Min(value = 1, message = "用户 id 至少为 1")
    @ApiModelProperty(value = "用户 id 不能为空,至少为 1", name = "userId", example = "0")
    private Long userId;

}
