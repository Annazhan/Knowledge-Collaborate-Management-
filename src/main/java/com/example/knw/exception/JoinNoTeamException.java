package com.example.knw.exception;

import com.example.knw.result.ResultEnum;

/**
 * 没有加入任何团队错误
 *
 * @author qanna
 * @date 2021-04-20
 */
public class JoinNoTeamException extends DefineException {
    public JoinNoTeamException() {
        super(ResultEnum.JOIN_NO_TEAM);
    }
}
