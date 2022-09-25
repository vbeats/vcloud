package com.codestepfish.admin.dto.open;

import com.codestepfish.common.model.PageIn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenConfigQueryIn extends PageIn {
    private Long tenantId;
    private String name;
}
