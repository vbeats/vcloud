package com.codestepfish.admin.dto.role;

import com.codestepfish.core.model.PageIn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleParamIn extends PageIn {
    private String roleName;
    private String action;

    private Long adminId;
}
