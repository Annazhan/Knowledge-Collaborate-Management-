package com.example.knw.exception;

import com.example.knw.result.ResultEnum;

/**
 * 项目已经存在报错
 *
 * @author qanna
 * @date 2021-04-26
 */
public class ProjectAlreadyExistException extends DefineException {
    public ProjectAlreadyExistException() {
        super(ResultEnum.PROJECT_ALREADY_EXIST);
    }
}
