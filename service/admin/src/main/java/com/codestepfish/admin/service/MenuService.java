package com.codestepfish.admin.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codestepfish.admin.entity.AdminRole;
import com.codestepfish.admin.entity.Menu;
import com.codestepfish.admin.entity.RoleMenu;
import com.codestepfish.admin.mapper.MenuMapper;
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
    private final AdminRoleService adminRoleService;

    public List<Tree<String>> menus() {
        LambdaQueryWrapper<Menu> wrapper = Wrappers.lambdaQuery();
        List<Menu> menus;

        Long adminId = StpUtil.getLoginIdAsLong();

        if (!adminId.equals(1L)) {
            // 当前用户角色能看到的菜单 按钮

            Set<Long> roleIds = adminRoleService.list(Wrappers.<AdminRole>lambdaQuery().eq(AdminRole::getAdminId, adminId)).stream().map(AdminRole::getRoleId).collect(Collectors.toSet());

            List<RoleMenu> roleMenus = roleMenuService.list(Wrappers.<RoleMenu>lambdaQuery().in(RoleMenu::getRoleId, roleIds));

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
}
