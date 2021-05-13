package com.example.knw.utils.enumpackage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 用户加入团队中可能出现的状态
 *
 * @author qanna
 * @date 2021-04-16
 */
public enum JoinInTeamStatusEnum {
    IN(0, "在团队中"),
    OUT(1, "曾经加入过团队"),
    SUBMIT(2, "提交申请"),
    INVITED(3, "被邀请"),

    ;

    int code;
    String des;

    JoinInTeamStatusEnum(int code, String des){
        this.code = code;
        this.des = des;
    }

    @JsonValue
    public int getCode(){
        return code;
    }

    public String getString(){
        return des;
    }

    @JsonCreator
    public static JoinInTeamStatusEnum getElementById(Integer id){
        for(JoinInTeamStatusEnum join:JoinInTeamStatusEnum.values()){
            if(id == join.getCode()){
                return join;
            }
        }
        return null;
    }
}
