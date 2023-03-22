package com.codestepfish.core.util;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSON;
import com.codestepfish.core.constant.auth.AuthConstant;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * sa-token extra util
 */
public class ExtraUtil {

    /**
     * 获取指定token的租户id
     *
     * @param token
     * @return
     */
    public static Long getTenantId(String token) {
        Object tenantId = StringUtils.hasText(token) ? StpUtil.getExtra(token, AuthConstant.Extra.TENANT_ID) : StpUtil.getExtra(AuthConstant.Extra.TENANT_ID);
        return Long.valueOf(String.valueOf(tenantId));
    }

    /**
     * 获取当前用户的租户id
     *
     * @return
     */
    public static Long getTenantId() {
        return getTenantId(null);
    }

    /**
     * 获取指定token的role id
     *
     * @param token
     * @return
     */
    public static Long getRoleId(String token) {
        Object roleId = StringUtils.hasText(token) ? StpUtil.getExtra(token, AuthConstant.Extra.ROLE_ID) : StpUtil.getExtra(AuthConstant.Extra.ROLE_ID);
        return Long.valueOf(String.valueOf(roleId));
    }

    /**
     * 获取当前用户的role id
     *
     * @return
     */
    public static Long getRoleId() {
        return getRoleId(null);
    }

    /**
     * 获取指定token的用户是否是超级管理员
     *
     * @param token
     * @return
     */
    public static Boolean isSuperAdmin(String token) {
        Object isSuperAdmin = StringUtils.hasText(token) ? StpUtil.getExtra(token, AuthConstant.Extra.IS_SUPER_ADMIN) : StpUtil.getExtra(AuthConstant.Extra.IS_SUPER_ADMIN);
        return Boolean.valueOf(String.valueOf(isSuperAdmin));
    }

    /**
     * 获取当前用户是否是超级管理员
     *
     * @return
     */
    public static Boolean isSuperAdmin() {
        return isSuperAdmin(null);
    }

    /**
     * 获取指定token的租户编号
     *
     * @param token
     * @return
     */
    public static String getTenantCode(String token) {
        Object tenantCode = StringUtils.hasText(token) ? StpUtil.getExtra(token, AuthConstant.Extra.TENANT_CODE) : StpUtil.getExtra(AuthConstant.Extra.TENANT_CODE);
        return String.valueOf(tenantCode);
    }

    /**
     * 获取当前用户的租户编号
     *
     * @return
     */
    public static String getTenantCode() {
        return getTenantCode(null);
    }

    /**
     * 获取指定token的租户名称
     *
     * @param token
     * @return
     */
    public static String getTenantName(String token) {
        Object tenantName = StringUtils.hasText(token) ? StpUtil.getExtra(token, AuthConstant.Extra.TENANT_NAME) : StpUtil.getExtra(AuthConstant.Extra.TENANT_NAME);
        return String.valueOf(tenantName);
    }

    /**
     * 获取当前用户的租户名称
     *
     * @return
     */
    public static String getTenantName() {
        return getTenantName(null);
    }

    /**
     * 获取指定token的roles集合
     *
     * @param token
     * @return
     */
    public static List<String> getRoles(String token) {
        Object roles = StringUtils.hasText(token) ? StpUtil.getExtra(token, AuthConstant.Extra.ROLES) : StpUtil.getExtra(AuthConstant.Extra.ROLES);
        return JSON.parseArray(String.valueOf(roles), String.class);
    }

    /**
     * 获取当前用户的roles集合
     *
     * @return
     */
    public static List<String> getRoles() {
        return getRoles(null);
    }

    /**
     * 获取指定token的permissions集合
     *
     * @param token
     * @return
     */
    public static List<String> getPermissions(String token) {
        Object permissions = StringUtils.hasText(token) ? StpUtil.getExtra(token, AuthConstant.Extra.PERMISSIONS) : StpUtil.getExtra(AuthConstant.Extra.PERMISSIONS);
        return JSON.parseArray(String.valueOf(permissions), String.class);
    }

    /**
     * 获取当前用户的permissions集合
     *
     * @return
     */
    public static List<String> getPermissions() {
        return getPermissions(null);
    }

    /**
     * 获取指定token的data scopes集合
     *
     * @param token
     * @return
     */
    public static List<Long> getDataScopes(String token) {
        Object dataScopes = StringUtils.hasText(token) ? StpUtil.getExtra(token, AuthConstant.Extra.DATA_SCOPES) : StpUtil.getExtra(AuthConstant.Extra.DATA_SCOPES);
        return JSON.parseArray(String.valueOf(dataScopes), Long.class);
    }

    /**
     * 获取当前用户的data scopes集合
     *
     * @return
     */
    public static List<Long> getDataScopes() {
        return getDataScopes(null);
    }
}
