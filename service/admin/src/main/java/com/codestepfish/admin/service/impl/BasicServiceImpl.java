package com.codestepfish.admin.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codestepfish.admin.dto.menu.MenuQueryParam;
import com.codestepfish.admin.service.*;
import com.codestepfish.common.constant.config.ConfigParamEnum;
import com.codestepfish.common.model.AppUser;
import com.codestepfish.datasource.entity.*;
import com.codestepfish.datasource.service.ConfigParamService;
import com.google.common.base.Splitter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BasicServiceImpl implements BasicService {

    private final MenuService menuService;
    private final RoleMenuService roleMenuService;
    private final TopMenuService topMenuService;
    private final TopMenuTreeService topMenuTreeService;
    private final ConfigParamService configParamService;

    @Override
    public List<Tree<String>> menus(MenuQueryParam param, AppUser user) {

        LambdaQueryWrapper<Menu> lambdaQuery = Wrappers.lambdaQuery();
        List<Menu> menus;

        if (!param.getIsAll()) {
            // 当前用户角色能看到的菜单 按钮
            List<RoleMenu> roleMenus = roleMenuService.list(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, user.getRoleId()));

            if (CollectionUtils.isEmpty(roleMenus)) {
                return null;
            }

            Set<Long> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toSet());

            if (!ObjectUtils.isEmpty(param.getTopMenuId())) {
                List<TopMenuTree> menuTrees = topMenuTreeService.list(Wrappers.<TopMenuTree>lambdaQuery().eq(TopMenuTree::getTopId, param.getTopMenuId()));
                menuIds.addAll(menuTrees.stream().map(TopMenuTree::getMenuId).collect(Collectors.toSet()));
            }

            lambdaQuery.in(Menu::getId, menuIds);
        } else {
            // 当前租户 分配的所有菜单 按钮
            if (!user.getTenantCode().equals(configParamService.getConfigByKey(ConfigParamEnum.SUPER_TENANT.getKey()))) {
                ConfigParam tenantDefaultMenu = configParamService.getConfigByKey(ConfigParamEnum.TENANT_MENU.getKey());
                List<Menu> ms = menuService.list(Wrappers.<Menu>lambdaQuery().in(Menu::getKey, Splitter.on(",").omitEmptyStrings().trimResults().splitToList(tenantDefaultMenu.getConfigValue())));
                Set<Long> ids = ms.stream().map(Menu::getId).collect(Collectors.toSet());
                lambdaQuery.in(Menu::getId, ids).or().in(Menu::getPId, ids);
            }
        }

        menus = menuService.list(lambdaQuery.isNull(Menu::getDeleteTime));

        TreeNodeConfig config = new TreeNodeConfig();
        config.setWeightKey("sort");
        config.setDeep(3);
        config.setParentIdKey("pId");

        return TreeUtil.build(menus, "0", config, (node, treeNode) -> {
            treeNode.setId(String.valueOf(node.getId()));
            treeNode.setParentId(String.valueOf(node.getPId()));
            treeNode.putExtra("title", node.getTitle());
            treeNode.putExtra("path", node.getPath());
            treeNode.putExtra("key", node.getKey());
            treeNode.putExtra("icon", node.getIcon());
            treeNode.putExtra("type", node.getType().getValue());
        });
    }

    @Override
    public List<TopMenu> topMenus(AppUser user) {
        return topMenuService.list(Wrappers.<TopMenu>lambdaQuery().in(TopMenu::getTenantCode, user.getTenantCode()).orderByAsc(TopMenu::getSort));
    }
}
