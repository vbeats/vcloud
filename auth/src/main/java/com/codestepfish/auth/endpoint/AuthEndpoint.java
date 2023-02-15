package com.codestepfish.auth.endpoint;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.codestepfish.auth.dto.AuthParam;
import com.codestepfish.auth.dto.AuthResponse;
import com.codestepfish.auth.dto.Captcha;
import com.codestepfish.auth.service.AuthService;
import com.codestepfish.core.result.R;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@RequestMapping("/oauth")
public class AuthEndpoint {

    private final AuthService authService;

    @PostMapping("/token")
    public AuthResponse token(@Valid @RequestBody AuthParam param, BindingResult result) {
        R.handleErr(result);
        return authService.token(param);
    }

    @SaCheckLogin
    @GetMapping("/refresh")
    public SaTokenInfo tokenInfo() {
        return StpUtil.getTokenInfo();
    }

    @GetMapping("/captcha")
    public Captcha getCaptcha() {
        return authService.getCaptcha();
    }

    @SaCheckLogin
    @GetMapping("/logout")
    public void logout() {
        StpUtil.logout();
    }
}
