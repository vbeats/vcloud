package com.codestepfish.auth.dto;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.codestepfish.common.serializer.LongToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthResponse {
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;  // 用户id (admin or user)
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long tenantId;

    private SaTokenInfo token;
}
