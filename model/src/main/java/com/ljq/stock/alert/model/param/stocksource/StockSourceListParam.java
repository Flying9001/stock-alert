package com.ljq.stock.alert.model.param.stocksource;

import com.ljq.stock.alert.model.BasePageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

/**
 * 股票源分页查询
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Data
@ToString(callSuper = true)
@ApiModel(value = "股票源分页查询", description = "股票源分页查询")
public class StockSourceListParam extends BasePageParam {

    private static final long serialVersionUID = 1L;

    /**
     * 市场类型,1-上海,2-深圳,3-香港,4-美国
     * */
    @Min(value = 1, message = "市场类型设置错误")
    @Max(value = 2, message = "市场类型设置错误")
    @ApiModelProperty(value = "市场类型,1-上海,2-深圳", name = "marketType", example = "0")
    private Integer marketType;
    /**
     * 股票代码
     * */
    @Pattern(regexp = "^[0-9a-zA-Z]{0,10}$", message = "股票代码不合法，请重新输入")
    @ApiModelProperty(value = "股票代码", name = "stockCode")
    private String stockCode;
    /**
     * 公司名称
     * */
    @Length(max = 20, message = "公司名称不能超过20字符")
    @ApiModelProperty(value = "公司名称", name = "companyName")
    private String companyName;




}
