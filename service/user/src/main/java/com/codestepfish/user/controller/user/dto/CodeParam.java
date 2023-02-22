package com.codestepfish.user.controller.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeParam {
    private String appid;
    private String code;  // 微信 授权code
}
