package com.codestepfish.core.constant.auth;

public class AuthConstant {
    // ********************* 特殊角色名  ************************

    public static final String SUPER_ADMIN = "super_admin";  // 超级管理员
    public static final String COO = "coo";  // 运营
    public static final String AGENT = "agent";  // 代理


    // ******************* token Extra 字段名************************
    // ******************   账号密码  ***************************
    public static final String DEFAULT_PASSWORD = "123456";   // 默认密码
    public static final String PASSWORD_RULE = "%s*%s";   // 密码规则   account + salt + password

    public static final class Extra {
        public static final String ROLE_ID = "roleId";   // 角色id
        public static final String IS_SUPER_ADMIN = "isSuperAdmin";   // 是否是超级管理员
        public static final String TENANT_ID = "tenantId";   // 租户id
        public static final String TENANT_CODE = "tenantCode"; // 租户编号
        public static final String TENANT_NAME = "tenantName"; // 租户名称
        public static final String ROLES = "roles";   // 角色集合
        public static final String PERMISSIONS = "permissions";   // 权限集合
        public static final String DATA_SCOPES = "dataScopes";   // 数据权限 当前租户 & 所有子级
    }
}
