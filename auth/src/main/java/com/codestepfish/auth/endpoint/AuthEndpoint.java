package com.codestepfish.auth.endpoint;

import com.codestepfish.auth.dto.AuthParam;
import com.codestepfish.auth.dto.AuthResponse;
import com.codestepfish.auth.dto.Captcha;
import com.codestepfish.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AuthEndpoint {

    private final AuthService authService;

    @PostMapping("/token")
    public AuthResponse token(@RequestBody AuthParam param) {
        log.info("用户认证: {}", param);
        return authService.token(param);
    }

    @GetMapping("/captcha")
    public Captcha getCaptcha() {
        return authService.getCaptcha();
    }
}
