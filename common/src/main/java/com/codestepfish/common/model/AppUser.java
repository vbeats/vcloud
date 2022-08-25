package com.codestepfish.common.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AppUser implements Serializable {
    private Long id;  // 用户id
    private Long roleId; // 管理员用户 角色id
    private String tenantCode; // 租户编号
}
