package com.codestepfish.core.config.auth;

import cn.dev33.satoken.stp.StpInterface;
import com.codestepfish.core.util.ExtraUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StpInterfaceImpl implements StpInterface {

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> perms = ExtraUtil.getPermissions();
        return CollectionUtils.isEmpty(perms) ? Collections.emptyList() : perms;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> roles = ExtraUtil.getRoles();
        return CollectionUtils.isEmpty(roles) ? Collections.emptyList() : roles;
    }

}
