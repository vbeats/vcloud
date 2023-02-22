package com.codestepfish.auth.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WxMaParam { // 微信小程序认证参数

    // 微信login code
    private String code;

    // 小程序appid
    private String appid;
}
