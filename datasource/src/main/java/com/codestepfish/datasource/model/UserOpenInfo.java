package com.codestepfish.datasource.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class UserOpenInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = -2372121602394784507L;

    private String unionid;

    private WxMiniAppUserInfo wxMiniApp;  // 微信小程序用户信息
}
