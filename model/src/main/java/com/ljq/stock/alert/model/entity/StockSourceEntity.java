package com.ljq.stock.alert.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ljq.stock.alert.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 股票源实体类
 *
 * @author junqiang.lu
 * @date 2021-03-22 17:17:06
 */
@Data
@TableName(value = "STOCK_SOURCE", resultMap = "stockSourceMap")
@ApiModel(value = "股票源", description = "股票源")
public class StockSourceEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

    /**
     * 市场类型,1-上海,2-深圳,3-香港,4-美国
     **/
    @TableField(value = "MARKET_TYPE")
    @ApiModelProperty(value = "市场类型,1-上海,2-深圳,3-香港,4-美国", name = "marketType")
    private Integer marketType;
    /**
     * 市场类型编码
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "市场类型编码", name = "marketTypeCode")
    private String marketTypeCode;
    /**
     * 股票代码
     **/
    @TableField(value = "STOCK_CODE")
    @ApiModelProperty(value = "股票代码", name = "stockCode")
    private String stockCode;
    /**
     * 公司名称
     **/
    @TableField(value = "COMPANY_NAME")
    @ApiModelProperty(value = "公司名称", name = "companyName")
    private String companyName;
    /**
     * 今日开盘价
     **/
    @TableField(value = "TODAY_START_PRICE")
    @ApiModelProperty(value = "今日开盘价", name = "todayStartPrice")
    private BigDecimal todayStartPrice;
    /**
     * 昨日收盘价
     **/
    @TableField(value = "YESTERDAY_END_PRICE")
    @ApiModelProperty(value = "昨日收盘价", name = "yesterdayEndPrice")
    private BigDecimal yesterdayEndPrice;
    /**
     * 当前股价
     **/
    @TableField(value = "CURRENT_PRICE")
    @ApiModelProperty(value = "当前股价", name = "currentPrice")
    private BigDecimal currentPrice;
    /**
     * 涨跌额
     **/
    @TableField(value = "INCREASE")
    @ApiModelProperty(value = "涨跌额", name = "increase")
    private BigDecimal increase;
    /**
     * 涨跌百分比
     **/
    @TableField(value = "INCREASE_PER")
    @ApiModelProperty(value = "涨跌百分比", name = "increasePer")
    private BigDecimal increasePer;
    /**
     * 今日最高价
     **/
    @TableField(value = "TODAY_MAX_PRICE")
    @ApiModelProperty(value = "今日最高价", name = "todayMaxPrice")
    private BigDecimal todayMaxPrice;
    /**
     * 今日最低价
     **/
    @TableField(value = "TODAY_MIN_PRICE")
    @ApiModelProperty(value = "今日最低价", name = "todayMinPrice")
    private BigDecimal todayMinPrice;
    /**
     * 交易量(单位:手)
     **/
    @TableField(value = "TRADE_NUMBER")
    @ApiModelProperty(value = "交易量", name = "tradeNumber")
    private Integer tradeNumber;
    /**
     * 交易金额
     **/
    @TableField(value = "TRADE_AMOUNT")
    @ApiModelProperty(value = "交易金额", name = "tradeAmount")
    private BigDecimal tradeAmount;
    /**
     * 日期
     **/
    @TableField(value = "DATE")
    @ApiModelProperty(value = "日期", name = "date")
    private String date;
    /**
     * 时间
     **/
    @TableField(value = "TIME")
    @ApiModelProperty(value = "时间", name = "time")
    private String time;

}
