package com.codestepfish.admin.dto.admin;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdminInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 8689021123022990148L;

    private String username;
    private String phone;
}
