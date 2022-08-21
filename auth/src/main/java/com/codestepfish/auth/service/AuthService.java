package com.codestepfish.auth.service;

import com.codestepfish.auth.dto.AuthParam;
import com.codestepfish.auth.dto.AuthResponse;
import com.codestepfish.auth.dto.Captcha;

public interface AuthService {
    AuthResponse token(AuthParam param);

    Captcha getCaptcha();
}
