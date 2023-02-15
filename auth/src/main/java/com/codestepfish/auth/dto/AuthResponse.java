package com.codestepfish.auth.dto;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.codestepfish.core.model.AppUser;
import com.codestepfish.core.serializer.LongToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthResponse {
    private AppUser user;

    private SaTokenInfo token;
}
