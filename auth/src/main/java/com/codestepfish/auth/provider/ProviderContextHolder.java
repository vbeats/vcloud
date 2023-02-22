package com.codestepfish.auth.provider;

import cn.hutool.extra.spring.SpringUtil;
import com.codestepfish.core.constant.auth.GrantTypeEnum;
import com.codestepfish.core.result.AppException;
import com.codestepfish.core.result.RCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProviderContextHolder {

    public static AuthProvider getAuthProvider(String grantType) {
        return switch (GrantTypeEnum.find(grantType)) {
            case PASSWORD -> SpringUtil.getBean(UserNamePasswordProvider.class);
            case WX_MA -> SpringUtil.getBean(WxMaAuthProvider.class);
            default -> {
                log.error("不支持的认证类型: {}", grantType);
                throw new AppException(RCode.PARAM_ERROR);
            }
        };
    }
}
