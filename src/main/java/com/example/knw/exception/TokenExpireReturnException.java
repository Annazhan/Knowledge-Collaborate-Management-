package com.example.knw.exception;

import com.example.knw.result.ResultEnum;

/**
 * token认证已经过期
 *
 * @author qanna
 * @date 2021-04-25
 */
public class TokenExpireReturnException extends DefineException {

    public TokenExpireReturnException() {
        super(ResultEnum.TOKEN_EXPIRE);
    }
}
