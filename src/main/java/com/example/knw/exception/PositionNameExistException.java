package com.example.knw.exception;

import com.example.knw.result.ResultEnum;

/**
 * 职位名称已经存在报错
 *
 * @author qanna
 * @date 2021-04-15
 */
public class PositionNameExistException extends DefineException{
    public PositionNameExistException() {
        super(ResultEnum.POSITION_NAME_EXIST);
    }
}
