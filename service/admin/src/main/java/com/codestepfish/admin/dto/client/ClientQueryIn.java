package com.codestepfish.admin.dto.client;


import com.codestepfish.common.model.PageIn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientQueryIn extends PageIn {
    private String clientId;
}
