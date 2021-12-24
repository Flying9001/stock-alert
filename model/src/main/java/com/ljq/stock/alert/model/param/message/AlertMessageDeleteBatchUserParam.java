package com.ljq.stock.alert.model.param.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @Description: 批量删除用户消息
 * @Author: junqiang.lu
 * @Date: 2021/4/15
 */
@Data
@ToString(callSuper = true)
@ApiModel(value = "批量删除用户消息", description = "批量删除用户消息")
public class AlertMessageDeleteBatchUserParam implements Serializable {

    private static final long serialVersionUID = -2876946836959375841L;

    /**
     * id 列表
     **/
    @NotEmpty(message = "id 不能为空")
    @ApiModelProperty(value = "id不能为空", name = "idList")
    private List<Long> idList;

}
