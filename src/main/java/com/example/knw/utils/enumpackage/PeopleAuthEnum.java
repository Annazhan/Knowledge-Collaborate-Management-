package com.example.knw.utils.enumpackage;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

/**
 * 人员变动操作类
 * @author  qanna
 * @date    2021-03-14
 */
public enum PeopleAuthEnum {
    /**
     * RESOURCE:发布资源、消息
     * TEAM:编辑团队信息，包括团队名称、简介、添加成员、邀请成员、移出团队、撤回消息
     * BOARD:发布公告
     * PROGRAM:编辑项目权限
     * TASK:编辑任务权限
     * AUTH:设置成员权限、查看成员权限、添加权限
     * KPI:查看成员贡献
     */
    RESOURCE(1),
    TEAM(2),
    BOARD(4),
    PROGRAM(8),
    TASK(16),
    AUTH(32),
    KPI(64),

    ;

    private int i;

    PeopleAuthEnum(int i) {
        this.i = i;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public static int getLeader(){
        int result = 0;
        for(PeopleAuthEnum p: PeopleAuthEnum.values()){
            result += p.getI();
        }
        return result;
    }

    public static int getAdmin(){
        int res = 0;
        for(PeopleAuthEnum p: PeopleAuthEnum.values()){
            if(!p.equals(PeopleAuthEnum.KPI)){
                res += p.getI();
            }
        }
        return res;
    }

    public static int getMember(){
        return PeopleAuthEnum.RESOURCE.getI();
    }

}