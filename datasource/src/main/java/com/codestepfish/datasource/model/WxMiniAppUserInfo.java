package com.codestepfish.datasource.model;

import com.codestepfish.common.serializer.LongToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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

    @JsonSerialize(using = LongToStringSerializer.class)
    private Long authorizerOpenConfigId; // 授权者的公众号/小程序 open config id

    private String openid;

    private String phone;  // 同一个微信 用户可能公众号/小程序使用不同手机号登陆
}
