package com.codestepfish.datasource.model;

import com.codestepfish.common.serializer.LongToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class UserOpenInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = -2372121602394784507L;

    @JsonSerialize(using = LongToStringSerializer.class)
    private Long openConfigId;  // 第三方开放平台id

    private WxMiniAppUserInfo wxMiniApp;  // 微信小程序用户信息
}
