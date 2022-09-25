package com.codestepfish.auth.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class AuthParam {

    @NotEmpty(message = "认证类型不能为空")
    private String grantType;  // 认证类型  GrantTypeEnum

    @NotEmpty(message = "客户端id不能为空")
    private String clientId; // 客户端id

    @NotEmpty(message = "客户端密钥不能为空")
    private String clientSecret; // 客户端密钥

    // ********************* refresh token*********************

    private String refreshToken;

    // ********************* 账号 密码登录 *********************

    private String tenantCode; // 租户编号

    private String account;

    private String password; // RSA 加密后的密码

    // ********************* 图形验证码 *********************

    private String key;

    private String code;

    // ******************** 微信相关参数 ************************
    private WxParam wxParam;
}
