package com.example.knw.exception;

import com.example.knw.result.ResultEnum;

/**
 * 自定义报错
 *
 * @author qanna
 * @date 2021-03-29
 */
public abstract class DefineException extends RuntimeException{
    private int errorCode;

    private String errorMsg;

    public DefineException(ResultEnum resultEnum){
        super();
        this.errorCode = resultEnum.getI();
        this.errorMsg = resultEnum.getS();
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int code) {
        this.errorCode = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String msg) {
        this.errorMsg = msg;
    }
}
