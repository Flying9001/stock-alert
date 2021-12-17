package com.ljq.stock.alert.service.util;

import cn.hutool.core.date.DateUtil;
import com.ljq.stock.alert.common.constant.CheckCodeTypeEnum;
import com.ljq.stock.alert.common.constant.MessageConst;
import com.ljq.stock.alert.common.constant.StockConst;
import com.ljq.stock.alert.model.entity.AlertMessageEntity;
import com.ljq.stock.alert.model.entity.UserStockEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Description: 消息辅助工具类
 * @Author: junqiang.lu
 * @Date: 2021/5/7
 */
public class MessageHelper {

    private MessageHelper(){
    }

    /**
     * 创建预警消息
     *
     * @param userStock
     * @return
     */
    public static AlertMessageEntity createAlertMessage(UserStockEntity userStock) {
        Date stockDate = DateUtil.parse(userStock.getStockSource().getDate() + " " +
                userStock.getStockSource().getTime(), "yyyy-MM-dd HH:mm:ss");
        // 股价对比
        int priceCompareResult = compareStockPrice(userStock.getStockSource().getCurrentPrice(),
                userStock.getMaxPrice(), userStock.getMinPrice(), stockDate.getTime());
        if (priceCompareResult != 0) {
            return getPriceLimitAlertMessage(userStock, priceCompareResult);
        }
        // 涨跌幅对比
        int increasePerCompareResult = compareIncreasePer(userStock.getStockSource().getIncreasePer(),
                userStock.getMaxIncreasePer(), userStock.getMaxDecreasePer(), stockDate.getTime());
        if (increasePerCompareResult != 0) {
            return getIncreasePerLimitAlertMessage(userStock, increasePerCompareResult);
        }
        return null;
    }

    /**
     * 批量创建预警消息
     *
     * @param userStockList
     * @return
     */
    public static List<AlertMessageEntity> createAlertMessageBatch(List<UserStockEntity> userStockList) {
        List<AlertMessageEntity> alertMessageList = new ArrayList<>();
        userStockList.stream().forEach(userStock -> {
            AlertMessageEntity alertMessage = createAlertMessage(userStock);
            if (Objects.nonNull(alertMessage)) {
                alertMessageList.add(alertMessage);
            }
        });
        return alertMessageList;
    }

    /**
     * 创建验证消息
     *
     * @param mobilePhone
     * @param email
     * @param checkCodeType
     * @param checkCode
     * @return
     */
    public static AlertMessageEntity createCheckMessage(String mobilePhone, String email,
                                                        CheckCodeTypeEnum checkCodeType, String checkCode) {
        AlertMessageEntity message = new AlertMessageEntity();
        message.setMobilePhone(mobilePhone);
        message.setEmail(email);
        StringBuilder contentBuilder = new StringBuilder("尊敬的用户,<br />");
        switch (checkCodeType) {
            case REGISTER:
                message.setTitle("【股价提醒小助手】注册验证");
                contentBuilder.append("【股价提醒小助手】欢迎你的加入,你的注册验证码为: ");
                break;
            case SIGN_IN:
                message.setTitle("【股价预警小助手】登录验证");
                contentBuilder.append("【股价提醒小助手】欢迎你回来,你的登录验证码为: ");
                break;
            case UPDATE_PASSWORD:
                message.setTitle("【股价预警小助手】修改密码验证");
                contentBuilder.append("【股价提醒小助手】为你的账户安全保驾护航,你正在进行修改密码操作,验证码为: ");
                break;
            default:
                break;
        }
        contentBuilder.append(checkCode)
                .append(",验证码有效时间为 10 分钟.安心炒股,从这里开始");
        message.setContent(contentBuilder.toString());
        return message;
    }

    /**
     * 股票价格比对
     * 当前股价 >= 用户设定最高股价,返回 1
     * 当前股价 <= 用户设定最低股价,返回 2
     * 用户设定最低股价 < 当前股价 < 用户设定最高股价,返回 0
     *
     * @param currentPrice 当前股价
     * @param userMaxPrice 用户设定最高股价
     * @param userMinPrice 用户设定最低股价
     * @param stockTimestamp 股票时间戳
     * @return
     */
    private static int compareStockPrice(BigDecimal currentPrice, BigDecimal userMaxPrice, BigDecimal userMinPrice,
                                        long stockTimestamp) {
        // 当前时间超出股价时间 5 分钟,即为失效,过滤收盘后的股价对比数据
        long timeLimit = 300000L;
        if ((System.currentTimeMillis() - stockTimestamp) > timeLimit) {
            return 0;
        }
        // 股价为 0 时失效,过滤开盘前无股价的数据
        if (currentPrice.compareTo(BigDecimal.ZERO) == 0) {
            return 0;
        }
        if (currentPrice.compareTo(userMaxPrice) > -1) {
            return 1;
        }
        if (currentPrice.compareTo(userMinPrice) < 1) {
            return 2;
        }
        return 0;
    }

    /**
     * 单日股价涨跌幅对比
     * 当涨跌幅 >= 最大涨幅限制,返回 1
     * 当涨跌幅 >= 最大跌幅限制,返回 2
     * 当最大跌幅限制 < 涨跌幅 < 最大涨幅限制,返回 0
     *
     * @param increasePer 股价涨跌幅
     * @param maxIncreasePer 用户设定最大涨幅限制
     * @param maxDecreasePer 用户设定最大跌幅限制
     * @param stockTimestamp 股价时间戳
     * @return
     */
    private static int compareIncreasePer(BigDecimal increasePer, int maxIncreasePer, int maxDecreasePer,
                                          long stockTimestamp) {
        // 当前时间超出股价时间 5 分钟,即为失效,过滤收盘后的涨跌幅对比数据
        long timeLimit = 300000L;
        if ((System.currentTimeMillis() - stockTimestamp) > timeLimit) {
            return 0;
        }
        // 过滤开盘前无效数据
        if (increasePer.abs().compareTo(BigDecimal.valueOf(100)) > -1) {
            return 0;
        }
        // 默认值
        if (StockConst.DEFAULT_INCREASE_PER_VALUE == maxIncreasePer
                || StockConst.DEFAULT_INCREASE_PER_VALUE == maxDecreasePer) {
            if (increasePer.compareTo(StockConst.DEFAULT_MAX_INCREASE_PER) > -1) {
                return 1;
            }
            if (increasePer.abs().compareTo(StockConst.DEFAULT_MAX_INCREASE_PER) > -1) {
                return 2;
            }
            return 0;
        }
        if (increasePer.compareTo(BigDecimal.valueOf(maxIncreasePer)) > -1) {
            return 1;
        }
        if (increasePer.compareTo(BigDecimal.ZERO) < 0
                && increasePer.abs().compareTo(BigDecimal.valueOf(maxDecreasePer)) > -1) {
            return 2;
        }
        return 0;
    }

    /**
     * 获取股价限制预警消息
     *
     * @param userStock 用户股票对象
     * @param compareResult 股价对比结果
     * @return
     */
    private static AlertMessageEntity getPriceLimitAlertMessage(UserStockEntity userStock, int compareResult) {
        AlertMessageEntity message = new AlertMessageEntity();
        message.setUserId(userStock.getUserId());
        message.setMobilePhone(userStock.getUserInfo().getMobilePhone());
        message.setPhoneSend(MessageConst.MESSAGE_SEND_NOT);
        message.setEmail(userStock.getUserInfo().getEmail());
        message.setEmailSend(MessageConst.MESSAGE_SEND_NOT);
        message.setAlertType(MessageConst.ALERT_TYPE_PRICE);
        message.setStockId(userStock.getStockSource().getId());
        String highOrLow = compareResult == 1 ? "高" : "低";
        String title = "股价提醒小助手-【" + userStock.getStockSource().getCompanyName() + "】最" + highOrLow + "股价提醒";
        message.setTitle(title);
        StringBuilder contentBuilder = new StringBuilder("尊敬的用户");
        contentBuilder.append(userStock.getUserInfo().getNickName()).append(",</br>")
                .append("你好!").append("你所关注的股票[").append(userStock.getStockSource().getCompanyName())
                .append("](").append(userStock.getStockSource().getStockCode()).append(")")
                .append("当前股价为: ").append(userStock.getStockSource().getCurrentPrice()).append(",")
                .append("你为该股票设定的预警值为最高价: ").append(userStock.getMaxPrice())
                .append(",最低价: ").append(userStock.getMinPrice()).append(",")
                .append("当前股价达到了最").append(highOrLow).append("股价预警值,请及时关注并交易");
        message.setContent(contentBuilder.toString());
        return message;
    }

    /**
     * 获取股价涨跌幅限制预警消息
     *
     * @param userStock 用户股票对象
     * @param compareResult 股价对比结果
     * @return
     */
    private static AlertMessageEntity getIncreasePerLimitAlertMessage(UserStockEntity userStock, int compareResult) {
        AlertMessageEntity message = new AlertMessageEntity();
        message.setUserId(userStock.getUserId());
        message.setMobilePhone(userStock.getUserInfo().getMobilePhone());
        message.setPhoneSend(MessageConst.MESSAGE_SEND_NOT);
        message.setEmail(userStock.getUserInfo().getEmail());
        message.setEmailSend(MessageConst.MESSAGE_SEND_NOT);
        message.setAlertType(MessageConst.ALERT_TYPE_INCREASE_PER);
        message.setStockId(userStock.getStockSource().getId());
        String highOrLow = compareResult == 1 ? "涨" : "跌";
        String title = "股价提醒小助手-【" + userStock.getStockSource().getCompanyName() + "】单日最大" + highOrLow + "幅提醒";
        message.setTitle(title);
        StringBuilder contentBuilder = new StringBuilder("尊敬的用户");
        contentBuilder.append(userStock.getUserInfo().getNickName()).append(",</br>")
                .append("你好!").append("你所关注的股票[").append(userStock.getStockSource().getCompanyName())
                .append("](").append(userStock.getStockSource().getStockCode()).append(")")
                .append("当前涨跌幅为: ").append(userStock.getStockSource().getIncreasePer()).append("%,")
                .append("你为该股票设定的单日最大涨幅: ")
                .append(userStock.getMaxIncreasePer() == StockConst.DEFAULT_INCREASE_PER_VALUE ?
                        StockConst.DEFAULT_MAX_INCREASE_PER + "%" : userStock.getMaxIncreasePer()).append("%,")
                .append("最大跌幅: ")
                .append(userStock.getMaxDecreasePer() == StockConst.DEFAULT_INCREASE_PER_VALUE ?
                        StockConst.DEFAULT_MAX_INCREASE_PER + "%" : userStock.getMaxDecreasePer()).append("%,")
                .append("当前股价达到了最大").append(highOrLow).append("幅预警值,请及时关注股价波动");
        message.setContent(contentBuilder.toString());
        return message;
    }


}
