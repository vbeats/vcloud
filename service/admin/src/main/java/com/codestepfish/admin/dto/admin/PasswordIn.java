package com.codestepfish.admin.dto.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordIn {
    private String clientId;
    private String clientSecret;
    private String password;
    private String newPassword;
}
