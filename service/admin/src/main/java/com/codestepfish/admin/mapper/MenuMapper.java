package com.codestepfish.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codestepfish.admin.dto.menu.MenuOut;
import com.codestepfish.datasource.entity.Menu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    List<MenuOut> listMenu();

    List<MenuOut> subMenu(Long pid);
}
