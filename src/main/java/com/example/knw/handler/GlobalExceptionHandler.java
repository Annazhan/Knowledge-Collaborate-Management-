package com.example.knw.handler;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.knw.exception.DefineException;
import com.example.knw.result.Result;
import com.example.knw.result.ResultEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局异常处理
 *
 * @author qanna
 * @date 2021-03-29
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = TokenExpiredException.class)
    @ResponseBody
    public Result tokenExpire(){
        return new Result(ResultEnum.TOKEN_EXPIRE, null);
    }

    @ExceptionHandler(value = MismatchedInputException.class)
    @ResponseBody
    public Result noneJsonString(){
        return new Result(ResultEnum.NONE_JSON_FIELD, "null");
    }

    @ExceptionHandler(value = DefineException.class)
    @ResponseBody
    public Result exceptionReturn(DefineException exception) {
        return new Result(exception);
    }

//    @ExceptionHandler(value = Exception.class)
//    @ResponseBody
//    public void otherExceptionReturn(HttpServletResponse response) throws IOException {
//        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        //return new Result(HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
