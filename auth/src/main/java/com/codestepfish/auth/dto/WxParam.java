package com.codestepfish.auth.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WxParam {
    // ********* 开放平台参数
    private String openAppId;

    private String appid;  // 微信appid  多平台共用一个参数名

    // ********* 微信端  , 敏感数据rsa加密后传输

    private String code; // code2session
}
