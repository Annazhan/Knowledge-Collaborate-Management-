package com.example.knw.result;

import com.example.knw.exception.DefineException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 返回给网页的数据
 *
 * @author qanna
 * @date 2021-03-29
 */
@Data
public class Result<T> implements Serializable {
    private boolean success = true;
    private String code = "00";
    private String msg = "请求成功";
    private T data;

    public Result( boolean success, int code, String msg, T data){
        this.success = success;
        this.code = String.format("%02d",code);
        this.msg = msg;
        this.data = data;
    }

    public Result(T data){
        if(data!=null){
            this.data = data;
        }
        else{
            this.success = false;
            this.code = "99";
            this.msg = "请求不成功，未知错误";
            this.data = null;
        }
    }

    public Result(ResultEnum resultEnum, T data){
        if(resultEnum.getI() < 10){
            this.success = true;
        }
        else {
            this.success = false;
        }
        this.code = String.format("%02d", resultEnum.getI());
        this.msg = resultEnum.getS();
        this.data = data;
    }

    public Result(DefineException e){
        this.success = false;
        this.code = String.format("%02d",e.getErrorCode());
        this.msg = e.getErrorMsg();
        this.data = null;
    }

    public Result(HttpStatus status){
        if(!status.isError()){
            this.success = true;
        }
        else{
            this.success = false;
        }
        this.code = String.valueOf(status.value());
        this.msg = status.getReasonPhrase();
        this.data = null;
    }

    public static String toString(Result result) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(result);
    }
}
