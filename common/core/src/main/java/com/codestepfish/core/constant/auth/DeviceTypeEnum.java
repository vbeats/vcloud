package com.codestepfish.core.constant.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 认证 token device
 */
@Getter
@AllArgsConstructor
public enum DeviceTypeEnum {
    WEB("web", "运营平台"),
    WX_APP("wx_app", "微信小程序"),
    ;

    private String device;
    private String desc;
}
