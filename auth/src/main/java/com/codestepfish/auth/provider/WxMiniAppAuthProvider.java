package com.codestepfish.auth.provider;

import com.codestepfish.auth.dto.AuthParam;
import com.codestepfish.auth.dto.AuthResponse;
import com.codestepfish.datasource.entity.AuthClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WxMiniAppAuthProvider implements AuthProvider {

    @Override
    public AuthResponse handleAuth(AuthParam param, AuthClient client) {
        return null;
    }
}
