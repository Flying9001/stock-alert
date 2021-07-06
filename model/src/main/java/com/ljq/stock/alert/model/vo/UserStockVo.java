package com.ljq.stock.alert.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description: 用户关注股票对象
 * @Author: junqiang.lu
 * @Date: 2021/5/11
 */
@Data
@ApiModel(value = "用户关注股票对象", description = "用户关注股票对象")
public class UserStockVo implements Serializable {

    private static final long serialVersionUID = 7804589123958857904L;

    /**
     * 用户信息
     **/
    @ApiModelProperty(value = "用户信息", name = "userId")
    private Long userId;
    /**
     * 昵称
     **/
    @ApiModelProperty(value = "昵称", name = "nickName")
    private String nickName;
    /**
     * 手机号
     **/
    @ApiModelProperty(value = "手机号", name = "mobilePhone")
    private String mobilePhone;
    /**
     * 邮箱
     **/
    @ApiModelProperty(value = "邮箱", name = "email")
    private String email;
    /**
     * 股票 id
     **/
    @ApiModelProperty(value = "股票 id", name = "stockId")
    private Long stockId;
    /**
     * 市场类型编码
     */
    @ApiModelProperty(value = "市场类型编码", name = "marketTypeCode")
    private String marketTypeCode;
    /**
     * 股票代码
     **/
    @ApiModelProperty(value = "股票代码", name = "stockCode")
    private String stockCode;
    /**
     * 公司名称
     **/
    @ApiModelProperty(value = "公司名称", name = "companyName")
    private String companyName;
    /**
     * 当前股价
     **/
    @ApiModelProperty(value = "当前股价", name = "currentPrice")
    private BigDecimal currentPrice;


}
