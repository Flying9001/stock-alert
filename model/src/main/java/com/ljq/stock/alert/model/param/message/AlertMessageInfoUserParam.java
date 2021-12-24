package com.ljq.stock.alert.model.param.message;

import com.ljq.stock.alert.model.BaseInfoParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

/**
 * @Description: 查询用户的消息详情
 * @Author: junqiang.lu
 * @Date: 2021/4/15
 */
@Data
@ToString(callSuper = true)
@ApiModel(value = "查询用户的消息详情", description = "查询用户的消息详情")
public class AlertMessageInfoUserParam extends BaseInfoParam {

    private static final long serialVersionUID = -7420816887461218767L;

}
