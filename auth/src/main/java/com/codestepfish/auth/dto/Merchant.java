package com.codestepfish.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * 商户
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Merchant implements Serializable {

    @Serial
    private static final long serialVersionUID = 2779190417236513615L;

    private Long id;

    /**
     * 商户编号
     */
    private String code;

    /**
     * 商户名称
     */
    private String merchantName;

}