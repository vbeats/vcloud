package com.codestepfish.admin.dto.admin;

import com.codestepfish.core.model.PageIn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminQueryIn extends PageIn {

    private Long merchantId;
    private String account;
    private String phone;
}
