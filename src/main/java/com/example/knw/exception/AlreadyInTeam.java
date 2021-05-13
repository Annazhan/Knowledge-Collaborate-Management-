package com.example.knw.exception;

import com.example.knw.result.ResultEnum;

/**
 * 已经存在于团队中
 *
 * @author qanna
 * @date 2021-04-26
 */
public class AlreadyInTeam extends DefineException {
    public AlreadyInTeam(ResultEnum resultEnum) {
        super(resultEnum);
    }
}
