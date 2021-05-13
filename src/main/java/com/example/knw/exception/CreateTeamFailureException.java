package com.example.knw.exception;

import com.example.knw.result.ResultEnum;

/**
 * 创建团队失败出错
 *
 * @author qanna
 * @date 2021-04-12
 */
public class CreateTeamFailureException extends DefineException {
    public CreateTeamFailureException(ResultEnum resultEnum){
        super(resultEnum);
    }
}
