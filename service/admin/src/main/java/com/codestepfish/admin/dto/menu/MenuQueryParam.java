package com.codestepfish.admin.dto.menu;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MenuQueryParam {
    private Boolean isAll = false; // 是否加载所有菜单数据
    private Long topMenuId; // 顶部菜单id
}
