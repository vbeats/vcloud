package com.codestepfish.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RCode {
    SUCCESS(200, "Success"),
    PARAM_ERROR(400, "Bad Request"),
    UNAUTHORIZED_ERROR(401, "Unauthorized"),
    ACCESS_DENY(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    GATEWAY_ERROR(503, "Gateway Blocked"),
    DEFAULT(600, "System Error"),
    ;

    private final Integer code;
    private final String msg;
}
