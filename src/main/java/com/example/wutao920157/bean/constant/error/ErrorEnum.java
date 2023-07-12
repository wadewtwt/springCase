package com.example.wutao920157.bean.constant.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@AllArgsConstructor
@Accessors(fluent = true)
public enum ErrorEnum {

    /**
     * 错误码及错误信息
     */
    SUCCESS("1", "操作成功"),
    NOT_SAME_PASSWORD("-10001", "密码不一致"),
    EXIST_USERNAME("-10002", "已存在相同的用户名"),
    LOGIN_FAIL("-10003", "账号或密码错误，请重试"),
    NOT_FOUND_USER("-10004", "未找到该用户"),
    LOGIN_TRY_LATER("-10005", "已达次数限制，请稍后重新尝试"),
    ERROR_SPECIAL_STRING("-10006", "用户名中有特殊字符"),
    ERROR_INSERT_LOG("-10007", "插入日志失败"),
    ERROR_TOKEN_PARSE("-10008", "TOKEN解析错误"),
    ERROR_TOKEN_NOT_EXiST("-10009", "无token，请重新登录"),
    ERROR_TOKEN_FORMAT("-10010", "token格式有误，请重新登录"),
    ERROR_IP_EMPTY("-10011", "header上的ip地址不能为空"),
    ERROR_REGISTER_FAIL("-10012", "注册失败，请稍后重试"),
    ERROR_UPDATE_OTHER_USER("-10013", "不可修改他人信息"),
    ERROR_ABOUT_SENSITIVE_WORD("-10014", "涉及敏感词，不可使用"),
    ERROR_NOT_EXIST_USER("-10014", "不存在该用户"),

    ;

    private String code;
    private String message;

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

