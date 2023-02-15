package com.codestepfish.auth.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class AuthParam {

    @NotEmpty(message = "认证类型不能为空")
    private String grantType;  // 认证类型  GrantTypeEnum

    // ********************* 账号 密码登录 *********************

    private String tenantCode; // 租户编号

    private String account;

    private String password; // 密码

    // ********************* 图形验证码 *********************

    private String key;

    private String code;

}
