package com.bootvue.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleUserQueryIn {
    private Integer current = 1;
    private Integer pageSize = 10;
    private String roleName;
}
