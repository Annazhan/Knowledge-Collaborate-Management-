package com.example.knw.utils.enumpackage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.http.HttpStatus;

/**
 * 用户级别权限
 *
 * @author qanna
 * @date 2021-04-14
 */
public enum UserLevelEnum {
    INDIVIDUAL(0, "个人用户",1024.0),
    MEMBERSHIP(1, "会员", 5120.0),
    ENTERPRISE(2, "企业用户",102400.0),
    ;

    private int code;
    private String s;
    private double space;

    UserLevelEnum(int code, String s, double space) {
        this.code = code;
        this.s = s;
        this.space = space;
    }

    @JsonValue
    public int getI() {
        return code;
    }

    public String getS() {
        return s;
    }

    public double getTeamSpace(){
        return space;
    }

    @JsonCreator
    public static UserLevelEnum getElementById(int code){
        for(UserLevelEnum level:UserLevelEnum.values()){
            if(level.getI()==code){
                return level;
            }
        }
        return null;
    }

}
