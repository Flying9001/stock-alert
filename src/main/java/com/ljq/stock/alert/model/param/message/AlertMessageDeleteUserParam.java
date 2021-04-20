package com.ljq.stock.alert.model.param.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Description: 删除用户单条消息
 * @Author: junqiang.lu
 * @Date: 2021/4/15
 */
@Data
@ApiModel(value = "删除用户单条消息", description = "删除用户单条消息")
public class AlertMessageDeleteUserParam extends AlertMessageDeleteParam {

    private static final long serialVersionUID = 2942442447249979434L;

    /**
     * 用户 id
     * */
    @NotNull(message = "用户 id 不能为空")
    @Min(value = 1, message = "用户 id 至少为 1")
    @ApiModelProperty(value = "用户 id 不能为空,至少为 1", name = "userId", required = true, example = "0")
    private Long userId;


}
