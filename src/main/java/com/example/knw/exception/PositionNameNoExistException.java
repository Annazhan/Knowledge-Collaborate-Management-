package com.example.knw.exception;

import com.example.knw.result.ResultEnum;

/**
 * 职位名称不存在报错
 *
 * @author qanna
 * @date 2021-04-15
 */
public class PositionNameNoExistException extends  DefineException{
    public PositionNameNoExistException() {
        super(ResultEnum.POSITION_NAME_NO_EXIST);
    }
}
