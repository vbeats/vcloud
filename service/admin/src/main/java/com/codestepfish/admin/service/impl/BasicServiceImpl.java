package com.codestepfish.admin.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codestepfish.admin.service.BasicService;
import com.codestepfish.admin.service.MenuService;
import com.codestepfish.admin.service.RoleMenuService;
import com.codestepfish.common.model.AppUser;
import com.codestepfish.datasource.entity.Menu;
import com.codestepfish.datasource.entity.RoleMenu;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BasicServiceImpl implements BasicService {

    private final MenuService menuService;
    private final RoleMenuService roleMenuService;

    @Override
    public List<Tree<Long>> menus(AppUser user) {

        LambdaQueryWrapper<Menu> lambdaQuery = Wrappers.lambdaQuery();

        List<RoleMenu> roleMenus = roleMenuService.list(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, user.getRoleId()));

        if (CollectionUtils.isEmpty(roleMenus)) {
            return null;
        }

        lambdaQuery.in(Menu::getId, roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toSet()));

        List<Menu> menus = menuService.list(lambdaQuery.isNull(Menu::getDeleteTime));

        TreeNodeConfig config = new TreeNodeConfig();
        config.setWeightKey("sort");
        config.setDeep(3);
        config.setParentIdKey("pId");

        return TreeUtil.build(menus, 0L, config, (node, treeNode) -> {
            treeNode.setId(node.getId());
            treeNode.setParentId(node.getPId());
            treeNode.putExtra("title", node.getTitle());
            treeNode.putExtra("path", node.getPath());
            treeNode.putExtra("key", node.getKey());
            treeNode.putExtra("icon", node.getIcon());
            treeNode.putExtra("type", node.getType().getValue());
        });
    }
}
