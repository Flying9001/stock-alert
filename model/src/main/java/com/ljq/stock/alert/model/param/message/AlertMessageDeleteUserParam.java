package com.ljq.stock.alert.model.param.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description: 删除用户单条消息
 * @Author: junqiang.lu
 * @Date: 2021/4/15
 */
@Data
@ToString(callSuper = true)
@ApiModel(value = "删除用户单条消息", description = "删除用户单条消息")
public class AlertMessageDeleteUserParam implements Serializable {

    private static final long serialVersionUID = 2942442447249979434L;

    /**
     * id
     **/
    @NotNull(message = "请选择需要删除的对象")
    @Min(value = 1, message = "请求参数不合理")
    @ApiModelProperty(value = "id", name = "id", required = true)
    private Long id;

}
