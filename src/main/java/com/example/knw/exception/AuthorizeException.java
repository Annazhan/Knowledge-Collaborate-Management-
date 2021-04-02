package com.example.knw.exception;

import com.example.knw.result.ResultEnum;

/**
 * 认证错误异常
 *
 * @author qanna
 * @date 2021-03-30
 */
public class AuthorizeException extends DefineException {
    public AuthorizeException() {
        super(ResultEnum.AUTHORIZE_FAILURE);
    }
}
