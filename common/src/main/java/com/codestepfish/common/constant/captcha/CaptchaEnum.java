package com.codestepfish.common.constant.captcha;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CaptchaEnum {
    CAPTCHA_KEY("captcha:line_%s", "图形验证码key"),
    SMS_KEY("code:sms_%s", "短信验证码key"),
    ;

    private String key;
    private String desc;
}
