package com.codestepfish.core.model;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser implements Serializable {
    private static final long serialVersionUID = -5836527549105263247L;

    private Long id;

    private Long roleId;

    private Long tenantId;

    private String account;

    private String nickName;

    private String phone;

    private Set<String> roles;  // 角色

    private Set<String> permissions; // 菜单按钮 权限

    private Set<Long> dataScopes;  // 数据权限
}
