package com.codestepfish.admin.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codestepfish.admin.entity.Menu;
import com.codestepfish.admin.entity.RoleMenu;
import com.codestepfish.admin.mapper.MenuMapper;
import com.codestepfish.core.constant.auth.AuthConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MenuService extends ServiceImpl<MenuMapper, Menu> implements IService<Menu> {

    private final RoleMenuService roleMenuService;
    private final MenuMapper menuMapper;

    public List<Tree<String>> menus() {
        LambdaQueryWrapper<Menu> wrapper = Wrappers.lambdaQuery();
        List<Menu> menus;

        Long adminId = StpUtil.getLoginIdAsLong();

        if (!adminId.equals(1L)) {
            // 当前用户角色能看到的菜单 按钮
            Long roleId = Long.valueOf(String.valueOf(StpUtil.getExtra(AuthConstant.Extra.ROLE_ID)));

            List<RoleMenu> roleMenus = roleMenuService.list(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, roleId));

            if (CollectionUtils.isEmpty(roleMenus)) {
                return null;
            }

            Set<Long> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toSet());

            wrapper.in(Menu::getId, menuIds);
        }

        menus = list(wrapper);

        TreeNodeConfig config = new TreeNodeConfig();
        config.setWeightKey("sort");
        config.setDeep(4);
        config.setParentIdKey("pid");

        return TreeUtil.build(menus, "0", config, (node, treeNode) -> {
            treeNode.setId(String.valueOf(node.getId()));
            treeNode.setParentId(String.valueOf(node.getPid()));
            treeNode.putExtra("title", node.getTitle());
            treeNode.putExtra("path", node.getPath());
            treeNode.putExtra("key", node.getKey());
            treeNode.putExtra("permission", node.getPermission());
            treeNode.putExtra("icon", node.getIcon());
            treeNode.putExtra("type", node.getType().getValue());
            treeNode.putExtra("sort", node.getSort());
        });
    }

    public List<Menu> listMenu() {
        return menuMapper.listMenu();
    }

    public List<Menu> subMenu(Long pid) {
        return menuMapper.subMenu(pid);
    }
}
