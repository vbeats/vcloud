package com.codestepfish.admin.service;

import cn.hutool.core.lang.tree.Tree;
import com.codestepfish.common.model.AppUser;

import java.util.List;

public interface BasicService {
    List<Tree<Long>> menus(AppUser user);
}
