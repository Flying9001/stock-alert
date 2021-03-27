package com.ljq.stock.alert.common.exception;

import com.ljq.stock.alert.common.api.ApiMsgEnum;
import lombok.Data;

/**
 * @Description: 自定义公共异常
 * @Author: junqiang.lu
 * @Date: 2020/9/4
 */
@Data
public class CommonException extends RuntimeException {

    /**
     * 异常编码 key
     */
    private String key;
    /**
     * 异常提示信息
     */
    private String message;

    public CommonException() {
        super();
    }

    public CommonException(Exception e) {
        super(e);
    }

    public CommonException(String message) {
        this.message = message;
    }

    public CommonException(ApiMsgEnum apiMsgEnum) {
        this.key = apiMsgEnum.getKey();
        this.message = apiMsgEnum.getDefaultMsg();
    }

    public CommonException(String key, String message) {
        this.key = key;
        this.message = message;
    }


}
