package com.codestepfish.admin.service;

import cn.hutool.core.lang.tree.Tree;
import com.codestepfish.admin.dto.menu.MenuQueryParam;
import com.codestepfish.common.model.AppUser;
import com.codestepfish.datasource.entity.TopMenu;

import java.util.List;

public interface BasicService {
    List<Tree<String>> menus(MenuQueryParam param, AppUser user);

    List<TopMenu> topMenus(AppUser user);
}
