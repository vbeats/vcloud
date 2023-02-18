package com.codestepfish.admin.dto.admin;

import com.codestepfish.admin.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class AssignAdminRolesIn {
    private Long adminId;
    private Set<Role> roles;
}
