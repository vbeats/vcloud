package com.codestepfish.core.constant.regex;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RegexEnum {
    ACCOUNT_REGEX("^[a-zA-Z]\\w{4,11}$", "账号 字母开头 字母数字_ 5-12位"),

    PHONE_REGEX("^(?:(?:\\+|00)86)?1(?:(?:3[\\d])|(?:4[5-79])|(?:5[0-35-9])|(?:6[5-7])|(?:7[0-8])|(?:8[\\d])|(?:9[189]))\\d{8}$", "手机号"),

    PASSWORD_REGEX("^\\S*(?=\\S{6,})(?=\\S*\\d)(?=\\S*[A-Z])(?=\\S*[a-z])(?=\\S*[!@#$%^&*? ])\\S*$", "密码 至少6位，包括至少1个大写字母，1个小写字母，1个数字，1个特殊字符"),
    ;

    private String regex;
    private String desc;
}
