package com.codestepfish.datasource.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserOpenInfo implements Serializable {
    private static final long serialVersionUID = -2372121602394784507L;

    private Long openId;  // 第三方开放平台id

    private WxMiniAppUserInfo wxMiniApp;  // 微信小程序用户信息
}
