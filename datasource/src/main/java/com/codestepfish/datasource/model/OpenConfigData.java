package com.codestepfish.datasource.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class OpenConfigData implements Serializable {
    @Serial
    private static final long serialVersionUID = -1185059056712960784L;

    private String appid;
    private String secret;
    private String token;
    private String aesKey;
    private String cloudEnv;

    // 公众号 h5域名前缀
    private String prefix;
}
