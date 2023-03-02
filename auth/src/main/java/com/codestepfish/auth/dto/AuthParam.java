package com.codestepfish.auth.dto;

import com.codestepfish.core.constant.auth.GrantTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class AuthParam {

    @NotNull(message = "认证类型不能为空")
    private GrantTypeEnum grantType;  // 认证类型

    // ********************* 账号 密码登录 *********************

    private String tenantCode; // 租户编号

    private String account;

    private String password; // 密码

    // ********************* 图形验证码 *********************

    private String key;

    private String code;

    // ******************** 微信小程序认证参数 *****************
    private WxMaParam wxMaParam;
}
