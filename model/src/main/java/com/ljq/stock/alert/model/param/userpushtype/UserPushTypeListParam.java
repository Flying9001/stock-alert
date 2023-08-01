package com.ljq.stock.alert.model.param.userpushtype;

import com.ljq.stock.alert.model.BasePageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * 用户消息推送方式参数接收类
 *
 * @author junqiang.lu
 * @date 2023-07-31 18:07:31
 */
@Data
@ApiModel(value = "用户消息推送方式查询列表", description = "用户消息推送方式查询列表")
public class UserPushTypeListParam extends BasePageParam {

    private static final long serialVersionUID = 1L;

    /**
     * id
     * */
    @Min(value = 1, message = "id 至少为 1")
    @ApiModelProperty(value = "id 不能为空,至少为 1", name = "id", example = "0")
    private Long id;

    /**
     * 是否启用，0-未启用，1-启用
     */
    @ApiModelProperty(value = "是否启用，0-未启用，1-启用", example = "1")
    private Integer enable;



}
