package com.codestepfish.admin.type;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
public enum MenuTypeEnum {  // 菜单类型
    MENU(0, "菜单"),
    BUTTON(1, "按钮");

    private static final Map<Integer, MenuTypeEnum> lookup = new HashMap<>();

    static {
        EnumSet.allOf(MenuTypeEnum.class).forEach(e -> lookup.put(e.getValue(), e));
    }

    @EnumValue
    private Integer value;
    private String type;

    public static MenuTypeEnum find(Integer value) {
        return lookup.get(value);
    }
}