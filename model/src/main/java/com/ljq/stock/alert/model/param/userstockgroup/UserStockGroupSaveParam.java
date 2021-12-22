package com.ljq.stock.alert.model.param.userstockgroup;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户股票分组参数接收类
 *
 * @author junqiang.lu
 * @date 2021-12-21 11:21:27
 */
@Data
@ApiModel(value = "用户股票分组保存(单条)", description = "用户股票分组保存(单条)")
public class UserStockGroupSaveParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分组名称
     * */
    @NotBlank(message = "分组名称 不能为空")
    @Length(max = 30, message = "分组名称需要控制在 30 字符以内")
    @ApiModelProperty(value = "分组名称", name = "groupName", required = true)
    private String groupName;

}
