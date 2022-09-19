package com.codestepfish.admin.dto.role;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class AssignRoleMenuIn {
    private Long roleId;
    private Set<Long> menuIds;
}
