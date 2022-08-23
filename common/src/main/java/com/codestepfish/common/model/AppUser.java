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
    private String tenantCode; // 租户编号
}
