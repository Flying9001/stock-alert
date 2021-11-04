package com.ljq.stock.alert.service.util;

import com.ljq.stock.alert.common.constant.CheckCodeTypeEnum;
import com.ljq.stock.alert.common.constant.MessageConst;
import com.ljq.stock.alert.model.entity.AlertMessageEntity;
import com.ljq.stock.alert.model.entity.UserStockEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    public static AlertMessageEntity createMessage(UserStockEntity userStock) {
        int priceCompareResult = compareStockPrice(userStock.getStockSource().getCurrentPrice(), userStock.getMaxPrice(),
                userStock.getMinPrice());
        if (priceCompareResult == 0) {
            return null;
        }
        AlertMessageEntity message = new AlertMessageEntity();
        message.setUserId(userStock.getUserId());
        message.setMobilePhone(userStock.getUserInfo().getMobilePhone());
        message.setPhoneSend(MessageConst.MESSAGE_SEND_NOT);
        message.setEmail(userStock.getUserInfo().getEmail());
        message.setEmailSend(MessageConst.MESSAGE_SEND_NOT);
        message.setStockId(userStock.getStockSource().getId());
        String highOrLow = priceCompareResult == 1 ? "高" : "低";
        String title = "股价提醒小助手-【" + userStock.getStockSource().getCompanyName() + "】最" + highOrLow + "股价提醒";
        message.setTitle(title);
        StringBuilder contentBuilder = new StringBuilder("尊敬的用户");
        contentBuilder.append(userStock.getUserInfo().getNickName()).append(",</br>")
                .append("你好!").append("你所关注的股票").append(userStock.getStockSource().getCompanyName())
                .append("(").append(userStock.getStockSource().getStockCode()).append(")")
                .append("当前股价为: ").append(userStock.getStockSource().getCurrentPrice()).append(",")
                .append("你为该股票设定的预警值为最高价: ").append(userStock.getMaxPrice())
                .append(",最低价: ").append(userStock.getMinPrice()).append(",")
                .append("当前股价达到了最").append(highOrLow).append("股价预警值,请及时关注并交易");
        message.setContent(contentBuilder.toString());
        return message;
    }

    /**
     * 批量创建预警消息
     *
     * @param userStockList
     * @return
     */
    public static List<AlertMessageEntity> createMessageBatch(List<UserStockEntity> userStockList) {
        List<AlertMessageEntity> alertMessageList = new ArrayList<>();
        userStockList.stream().forEach(userStock -> {
            AlertMessageEntity alertMessage = createMessage(userStock);
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
     * @return
     */
    public static int compareStockPrice(BigDecimal currentPrice, BigDecimal userMaxPrice, BigDecimal userMinPrice) {
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



}
