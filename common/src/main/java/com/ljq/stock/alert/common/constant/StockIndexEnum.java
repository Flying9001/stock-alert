package com.ljq.stock.alert.common.constant;

import lombok.Getter;
import lombok.ToString;

/**
 * @Description: 股票指数枚举类
 * @Author: junqiang.lu
 * @Date: 2022/2/9
 */
@Getter
@ToString
public enum StockIndexEnum {

    /**
     * 上证指数
     */
    s_sh000001,
    /**
     * 深圳成指
     */
    s_sz399001,
    /**
     * 沪深 300
     */
    s_sz399300,
    /**
     * 创业板指
     */
    s_sz399006,
    /**
     * 恒生指数
     */
    int_hangseng,
    /**
     * 道琼斯指数
     */
    int_dji,
    /**
     * 纳斯达克指数
     */
    int_nasdaq,
    /**
     * 标普 500指数
     */
    int_sp500,
    /**
     * 伦敦指数
     */
    int_ftse,
    /**
     * 中小板指数
     */
    s_sz399005,
    /**
     * 日经指数
     */
    int_nikkei,
    /**
     * 台湾台北指数
     */
    b_TWSE,
    /**
     * 富时新加坡海峡时报指数
     */
    b_FSSTI



}
