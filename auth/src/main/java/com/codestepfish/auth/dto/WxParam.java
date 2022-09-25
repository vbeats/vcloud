package com.codestepfish.auth.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WxParam {
    private String appid;  // 微信appid  多平台共用一个参数名

    // ********* 微信端 云开发 获取敏感数据  , 敏感数据rsa加密后传输

    private String openid; // 用户openid
    private String unionid;
    private String phone;
}
