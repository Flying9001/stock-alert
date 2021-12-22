package com.ljq.stock.alert.model.param.userstockgroup;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户股票分组参数接收类
 *
 * @author junqiang.lu
 * @date 2021-12-21 11:21:27
 */
@Data
@ApiModel(value = "用户股票分组修改(单条)", description = "用户股票分组修改(单条)")
public class UserStockGroupUpdateParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id,主键
     * */
    @NotNull(message = "id,主键 不能为空")
    @Min(value = 1, message = "id,主键 至少为 1")
    @ApiModelProperty(value = "id,主键 不能为空,至少为 1", name = "id", required = true, example = "0")
    private Long id;
    /**
     * 分组名称
     * */
    @NotBlank(message = "分组名称 不能为空")
    @Length(max = 30, message = "分组名称需要控制在 30 字符以内")
    @ApiModelProperty(value = "分组名称", name = "groupName", required = true)
    private String groupName;


}
