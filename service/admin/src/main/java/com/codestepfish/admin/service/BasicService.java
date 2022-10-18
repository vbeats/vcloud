package com.codestepfish.admin.service;

import cn.hutool.core.lang.tree.Tree;
import com.codestepfish.admin.dto.admin.AdminInfo;
import com.codestepfish.admin.dto.admin.PasswordIn;
import com.codestepfish.admin.dto.menu.MenuQueryParam;

import java.util.List;

public interface BasicService {
    List<Tree<String>> menus(MenuQueryParam param);

    AdminInfo profile();

    void updateProfile(PasswordIn param);
}
