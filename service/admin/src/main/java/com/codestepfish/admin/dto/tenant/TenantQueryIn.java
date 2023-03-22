package com.codestepfish.admin.dto.tenant;

import com.codestepfish.core.model.PageIn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TenantQueryIn extends PageIn {

    private Long pid = 0L;

    private String code;

    private String tenantName;
}
