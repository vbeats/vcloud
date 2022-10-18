package com.codestepfish.datasource.model;

import com.codestepfish.datasource.type.OpenTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class OpenItem implements Serializable {
    @Serial
    private static final long serialVersionUID = -8654480690924275799L;

    private String appid;
    private String name;
    private OpenTypeEnum type;
}
