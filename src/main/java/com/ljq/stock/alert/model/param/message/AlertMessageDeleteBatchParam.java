package com.ljq.stock.alert.model.param.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 预警消息批量删除
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Data
@ApiModel(value = "预警消息批量删除", description = "预警消息删除批量")
public class AlertMessageDeleteBatchParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id 列表
     **/
    @NotEmpty(message = "id 不能为空")
    @ApiModelProperty(value = "id不能为空", name = "idList")
    private List<Long> idList;


}
