package com.example.knw.utils.enumauth;

/**
 * 人员变动操作类
 * @author  qanna
 * @date    2021-03-14
 */
public enum PeopleAuthEnum {
    GETOUT(1), //"踢出群聊"
    CONSENT(2), //"同意加群"
    DROPOFF(4), //"撤回消息"
    ANNOUNCE(8), //"发公告"
    SEND(16), //"发送消息"
    TASK(32), //"添加、删除、修改任务"
    PROGRAM(64), //"添加、删除、标记项目"
    DELETECOMMENT(128), //删除评论
    KPI(256), //"查看组员贡献"

    LEADER(511), //"团队领导"
    ADMIN(255), //"管理员"
    MEMBER(0); //"组员"



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

}