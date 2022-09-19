package com.codestepfish.admin.dto.admin;

import com.codestepfish.common.model.PageIn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminQueryIn extends PageIn {

    private Long tenantId;
    private String account;
    private String phone;
}
