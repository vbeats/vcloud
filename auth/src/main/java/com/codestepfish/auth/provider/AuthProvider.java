package com.codestepfish.auth.provider;

import com.codestepfish.auth.dto.AuthParam;
import com.codestepfish.auth.dto.AuthResponse;
import com.codestepfish.datasource.entity.AuthClient;

public interface AuthProvider {
    AuthResponse handleAuth(AuthParam param, AuthClient client);
}
