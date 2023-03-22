package com.codestepfish.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 租户
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Tenant implements Serializable {

    @Serial
    private static final long serialVersionUID = 2779190417236513615L;

    private Long id;

    /**
     * 租户编号
     */
    private String code;

    /**
     * 租户名称
     */
    private String tenantName;

}