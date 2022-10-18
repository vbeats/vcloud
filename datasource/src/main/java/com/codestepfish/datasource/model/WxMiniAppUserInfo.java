package com.codestepfish.datasource.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WxMiniAppUserInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = -5250508666047963062L;

    private String openid;
    private String unionid;
    private String phone;
}
