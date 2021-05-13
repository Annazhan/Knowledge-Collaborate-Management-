package com.example.knw.exception;

import com.example.knw.result.ResultEnum;

/**
 * 发送消息失败
 *
 * @author qanna
 * @date 2021-04-21
 */
public class SendMsgFailureException extends DefineException {
    public SendMsgFailureException() {
        super(ResultEnum.SEND_MSG_FAILURE);
    }
}
