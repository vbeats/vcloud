package com.codestepfish.auth.provider;

import cn.hutool.extra.spring.SpringUtil;
import com.codestepfish.common.constant.auth.GrantTypeEnum;
import com.codestepfish.common.result.AppException;
import com.codestepfish.common.result.RCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProviderContextHolder {

    public static AuthProvider getAuthProvider(String grantType) {
        switch (GrantTypeEnum.find(grantType)) {
            case PASSWORD:  // 平台登陆 账号 密码
                return SpringUtil.getBean(UserNamePasswordProvider.class);
            case WX_MA: // c端用户 小程序认证
                return SpringUtil.getBean(WxMaAppAuthProvider.class);
            default:
                log.error("未支持的 grantType: {}", grantType);
                throw new AppException(RCode.PARAM_ERROR);
        }
    }
}
