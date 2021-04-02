package com.example.knw.result;

import lombok.Data;
import lombok.Getter;

/**
 * 最终可能的结果
 *
 * @author qanna
 * @date 2021-03-29
 */
@Getter
public enum ResultEnum {
    SUCCESS(00, "请求成功"),
    SUCCESS_REGISTER(01, "成功注册"),
    SUCCESS_LOGOUT(02, "成功退出"),

    USER_RELATED_FAILURE(10, "用户相关错误"),
    NO_SUCH_USER(11, "没有这个用户"),
    FALSE_PASSWORD(12, "用户名密码错误"),
    TOKEN_EXPIRE(13, "token失效"),
    AUTHORIZE_FAILURE(14, "认证异常"),
    ALREADY_REGISTER(15, "已经注册"),
    ALREADY_LOGOUT(16, "已经退出"),
    ALREADY_LOGIN(17, "已经登录"),

    UNKNOWN_FAIL(99, "未知错误，请求失败"),
    ;

    private int i;
    private String s;
    
    ResultEnum(int i, String s) {
        this.i = i;
        this.s = s;
    }
}
