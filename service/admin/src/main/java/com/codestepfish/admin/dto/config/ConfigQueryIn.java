package com.codestepfish.admin.dto.config;


import com.codestepfish.common.model.PageIn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfigQueryIn extends PageIn {
    private String configKey;
}
