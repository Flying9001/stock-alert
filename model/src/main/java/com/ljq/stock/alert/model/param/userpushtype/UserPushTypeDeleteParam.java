package com.ljq.stock.alert.model.param.userpushtype;

import com.ljq.stock.alert.model.BaseInfoParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 用户消息推送方式参数接收类
 *
 * @author junqiang.lu
 * @date 2023-07-31 18:07:31
 */
@Data
@ApiModel(value = "用户消息推送方式删除(单条)", description = "用户消息推送方式删除(单条)")
public class UserPushTypeDeleteParam extends BaseInfoParam {

    private static final long serialVersionUID = 1L;


}
