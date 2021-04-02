package com.example.knw.exception;

import com.example.knw.result.ResultEnum;

/**
 * 数据库找不到用户报错
 *
 * @author qanna
 * @date 2021-03-29
 */
public class NoSuchUserException extends DefineException {

    public NoSuchUserException() {
        super(ResultEnum.NO_SUCH_USER);
    }
}
