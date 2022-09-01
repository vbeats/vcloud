package com.codestepfish.auth.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
}
