package com.example.knw.utils.enumpackage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 用户状态枚举
 *
 * @author qanna
 * @date 2021-04-12
 */
public enum UserStatusEnum {
    ONLINE(0,"在线"),
    OFFLINE(1, "下线"),


    ;

    int i;

    String s;

    UserStatusEnum(int i, String s) {
        this.i = i;
        this.s = s;
    }

    @JsonCreator
    public static UserStatusEnum getEnumByID(int id){
        for(UserStatusEnum status:UserStatusEnum.values()){
            if(status.getI()==id){
                return status;
            }
        }
        return null;
    }

    @JsonValue
    public int getI() {
        return i;
    }

    public String getS() {
        return s;
    }
}
