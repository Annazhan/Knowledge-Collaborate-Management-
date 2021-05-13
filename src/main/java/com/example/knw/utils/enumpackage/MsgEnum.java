package com.example.knw.utils.enumpackage;

/**
 * 不同类别的消息
 *
 * @author qanna
 * @date 2021-04-08
 */
public enum MsgEnum {
    FILE('f',"文件/图片"),
    TEXT('t',"文本/代码"),
    NOTIFY('n',"系统通知"),
    DELETE('d',"撤回消息"),
    ;

    char c;
    String s;

    MsgEnum(char c, String s) {
        this.c = c;
        this.s = s;
    }

    public String getC() {
        return String.valueOf(c);
    }

    public String getS() {
        return s;
    }
}
