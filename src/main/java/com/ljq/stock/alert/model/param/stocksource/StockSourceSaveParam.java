package com.ljq.stock.alert.model.param.stocksource;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 股票源新增(单条)
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Data
@ApiModel(value = "股票源新增(单条)", description = "股票源新增(单条)")
public class StockSourceSaveParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 市场类型,1-上海,2-深圳,3-香港,4-美国
     * */
    @NotNull(message = "市场类型不能为空")
    @Min(value = 1, message = "市场类型设置错误")
    @Max(value = 4, message = "市场类型设置错误")
    @ApiModelProperty(value = "市场类型,1-上海,2-深圳,3-香港,4-美国 不能为空,至少为 1", name = "marketType", required = true, example = "0")
    private Integer marketType;
    /**
     * 股票代码
     * */
    @NotNull(message = "股票代码 不能为空")
    @ApiModelProperty(value = "股票代码", name = "stockCode", required = true)
    private String stockCode;
    /**
     * 公司名称
     * */
    @NotNull(message = "公司名称 不能为空")
    @ApiModelProperty(value = "公司名称", name = "companyName", required = true)
    private String companyName;
    /**
     * 今日开盘价
     * */
    @NotNull(message = "今日开盘价 不能为空")
    @ApiModelProperty(value = "今日开盘价", name = "todayStartPrice", required = true)
    private BigDecimal todayStartPrice;
    /**
     * 昨日收盘价
     * */
    @NotNull(message = "昨日收盘价 不能为空")
    @ApiModelProperty(value = "昨日收盘价", name = "yesterdayEndPrice", required = true)
    private BigDecimal yesterdayEndPrice;
    /**
     * 当前股价
     * */
    @NotNull(message = "当前股价 不能为空")
    @ApiModelProperty(value = "当前股价", name = "currentPrice", required = true)
    private BigDecimal currentPrice;
    /**
     * 涨跌额
     * */
    @NotNull(message = "涨跌额 不能为空")
    @ApiModelProperty(value = "涨跌额", name = "increase", required = true)
    private BigDecimal increase;
    /**
     * 涨跌百分比
     * */
    @NotNull(message = "涨跌百分比 不能为空")
    @ApiModelProperty(value = "涨跌百分比", name = "increasePer", required = true)
    private BigDecimal increasePer;
    /**
     * 今日最高价
     * */
    @NotNull(message = "今日最高价 不能为空")
    @ApiModelProperty(value = "今日最高价", name = "todayMaxPrice", required = true)
    private BigDecimal todayMaxPrice;
    /**
     * 今日最低价
     * */
    @NotNull(message = "今日最低价 不能为空")
    @ApiModelProperty(value = "今日最低价", name = "todayMinPrice", required = true)
    private BigDecimal todayMinPrice;
    /**
     * 交易量
     * */
    @NotNull(message = "交易量 不能为空")
    @Min(value = 1, message = "交易量 至少为 1")
    @ApiModelProperty(value = "交易量 不能为空,至少为 1", name = "tradeNumber", required = true, example = "0")
    private Integer tradeNumber;
    /**
     * 交易金额
     * */
    @NotNull(message = "交易金额 不能为空")
    @ApiModelProperty(value = "交易金额", name = "tradeAmount", required = true)
    private BigDecimal tradeAmount;
    /**
     * 日期
     * */
    @NotNull(message = "日期 不能为空")
    @ApiModelProperty(value = "日期", name = "date", required = true)
    private Date date;
    /**
     * 时间
     * */
    @NotNull(message = "时间 不能为空")
    @ApiModelProperty(value = "时间", name = "time", required = true)
    private String time;
    /**
     * 创建时间
     * */
    @NotNull(message = "创建时间 不能为空")
    @ApiModelProperty(value = "创建时间", name = "crateDate", required = true)
    private Date crateDate;
    /**
     * 更新时间
     * */
    @NotNull(message = "更新时间 不能为空")
    @ApiModelProperty(value = "更新时间", name = "updateDate", required = true)
    private Date updateDate;


}
