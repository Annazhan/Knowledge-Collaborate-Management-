package com.example.knw.utils.enumpackage;

/**
 * 文件操作权限类
 *
 * @author qanna
 * @date 2021-03-14
 */
public enum FileAuthEnum {
    LOOK(1),
    REVISE(2),
    DELETE(4),
    ;

    int i;
    FileAuthEnum(int i) {
        this.i = i;
    }
}
