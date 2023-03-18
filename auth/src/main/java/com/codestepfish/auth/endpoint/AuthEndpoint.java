package com.codestepfish.auth.endpoint;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.hutool.core.thread.ThreadUtil;
import com.codestepfish.auth.dto.AuthParam;
import com.codestepfish.auth.dto.AuthResponse;
import com.codestepfish.auth.dto.Captcha;
import com.codestepfish.auth.provider.ProviderContextHolder;
import com.codestepfish.auth.service.AuthService;
import com.codestepfish.core.result.R;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadPoolExecutor;


@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@RequestMapping("/oauth")
public class AuthEndpoint {

    private final AuthService authService;

    private final ThreadPoolExecutor testDtp;

    @PostMapping("/token")
    public AuthResponse token(@Valid @RequestBody AuthParam param, BindingResult result) {
        R.handleErr(result);
        return authService.token(param);
    }

    @SaCheckLogin
    @PostMapping("/refresh")
    public SaTokenInfo tokenInfo(@Valid @RequestBody AuthParam param, BindingResult result) {  // 延长token timeout有效期
        R.handleErr(result);
        return ProviderContextHolder.getAuthProvider(param.getGrantType()).refreshToken(param);
    }

    @GetMapping("/captcha")
    public Captcha getCaptcha() {
        for (int i = 0; i < 100; i++) {
            testDtp.execute(() -> {
                log.info("thread exec ....{}", Thread.currentThread().getName());
                ThreadUtil.safeSleep(3000L);
                log.info("thread exec finish ...{}", Thread.currentThread().getName());
            });
        }

        ThreadUtil.safeSleep(200000L);
        return authService.getCaptcha();
    }
}
