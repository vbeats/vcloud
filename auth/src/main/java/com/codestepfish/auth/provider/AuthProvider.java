package com.codestepfish.auth.provider;

import com.codestepfish.auth.dto.AuthParam;
import com.codestepfish.auth.dto.AuthResponse;

public interface AuthProvider {
    AuthResponse handleAuth(AuthParam param);
}
