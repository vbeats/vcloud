package com.codestepfish.datasource.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codestepfish.datasource.entity.Menu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> listMenu();

    List<Menu> subMenu(Long pid);
}
