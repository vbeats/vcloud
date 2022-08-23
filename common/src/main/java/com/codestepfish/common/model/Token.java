package com.codestepfish.common.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
/*
  access token 或 refresh token
*/
@Slf4j
public class Token implements Serializable {
    private static final long serialVersionUID = -1962424963088919992L;

    private Long id;   // 用户id
    private String tokenType;  // token类型   access_token | refresh_token
    private String userType; // 用户类型   admin | user
}
