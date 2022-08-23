package com.codestepfish.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageIn {
    private Long current = 1L;
    private Long pageSize = 15L;
}
