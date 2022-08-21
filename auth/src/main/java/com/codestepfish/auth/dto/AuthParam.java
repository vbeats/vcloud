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
    private String grantType;  // 认证类型  mini_app , mp , password , refresh_token.....

    private String tenantCode; // 租户编号

    // ********************* refresh token*********************

    private String refreshToken;

    // ********************* 账号 密码登录 *********************

    private String account;

    private String password;

    // ********************* 图形验证码 *********************

    private String key;

    private String code;
}
