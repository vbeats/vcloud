package com.codestepfish.auth.endpoint;

import com.codestepfish.auth.dto.AuthParam;
import com.codestepfish.auth.dto.AuthResponse;
import com.codestepfish.auth.dto.Captcha;
import com.codestepfish.auth.service.AuthService;
import com.codestepfish.common.result.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @GetMapping("/captcha")
    public Captcha getCaptcha() {
        return authService.getCaptcha();
    }
}
