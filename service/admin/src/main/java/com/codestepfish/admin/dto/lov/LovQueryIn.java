package com.codestepfish.admin.dto.lov;

import com.codestepfish.core.model.PageIn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LovQueryIn extends PageIn {
    private Long merchantId;
    private Long lovCategoryId;
    private String category;
    private String key;
}
