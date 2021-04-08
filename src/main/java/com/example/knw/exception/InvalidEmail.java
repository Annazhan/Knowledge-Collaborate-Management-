package com.example.knw.exception;

import com.example.knw.result.ResultEnum;

/**
 * 错误邮箱格式
 *
 * @author qanna
 * @date 2021-04-08
 */
public class InvalidEmail extends DefineException {

    public InvalidEmail() {
        super(ResultEnum.INVALID_EMAIL);
    }
}
