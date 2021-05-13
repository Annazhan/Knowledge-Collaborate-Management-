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
    SUCCESS_CHANGE_PASSWORD(03, "成功修改密码"),

    USER_RELATED_FAILURE(10, "用户相关错误"),
    NO_SUCH_USER(11, "没有这个用户"),
    FALSE_PASSWORD(12, "用户名密码错误"),
    TOKEN_EXPIRE(13, "token失效"),
    AUTHORIZE_FAILURE(14, "认证异常"),
    ALREADY_REGISTER(15, "已经注册"),
    ALREADY_LOGOUT(16, "已经退出"),
    ALREADY_LOGIN(17, "已经登录"),
    CODE_INVALID(18, "验证码错误"),
    INVALID_EMAIL(19, "非法邮箱"),

    TEAM_RELATED_FAILURE(20, "团队相关错误"),
    CREATE_TEAM_FAILURE(21,"创建团队失败"),
    POSITION_NAME_EXIST(22, "团队职位名称已经存在"),
    POSITION_NAME_NO_EXIST(23, "团队职位名称不存在"),
    JOIN_NO_TEAM(24, "没有加入团队"),
    TEAM_ALREADY_EXIST(25, "团队已经存在"),
    ALREADY_SUBMIT_APPLICATION(26, "已经提交了申请"),
    ALREADY_GET_INVITATION(27, "已经收到了邀请"),
    ALREADY_IN_TEAM(28, "已经加入团队中"),
    UPDATE_TEAM_INFO_FAILURE(29, "修改团队信息失败"),
    DELETE_TEAMMATES_FAILURE(30, "踢出队员失败"),
    ALREADY_TEAM_LEADER(31, "已经是队长"),

    MSG_RELATED_FAILURE(40, "消息相关错误"),
    SEND_MSG_FAILURE(41,"发送消息失败"),

    PROJECT_RELATED_FAILURE(50, "项目相关错误"),
    PROJECT_ALREADY_EXIST(51, "项目已经存在"),
    CREATE_PROJECT_FAILURE(52, "创建项目失败"),

    NONE_JSON_FIELD(98, "没有指定输入"),
    UNKNOWN_FAIL(99, "未知错误，请求失败"),
    ;

    private int i;
    private String s;
    
    ResultEnum(int i, String s) {
        this.i = i;
        this.s = s;
    }
}
