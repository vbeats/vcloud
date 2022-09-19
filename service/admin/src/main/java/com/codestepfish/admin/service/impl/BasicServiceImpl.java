package com.codestepfish.admin.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codestepfish.admin.dto.admin.AdminInfo;
import com.codestepfish.admin.dto.admin.PasswordIn;
import com.codestepfish.admin.dto.menu.MenuQueryParam;
import com.codestepfish.admin.service.*;
import com.codestepfish.common.model.AppUser;
import com.codestepfish.common.result.AppException;
import com.codestepfish.common.result.RCode;
import com.codestepfish.common.util.RsaUtil;
import com.codestepfish.datasource.entity.Admin;
import com.codestepfish.datasource.entity.AuthClient;
import com.codestepfish.datasource.entity.Menu;
import com.codestepfish.datasource.entity.RoleMenu;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BasicServiceImpl implements BasicService {

    private final MenuService menuService;
    private final RoleMenuService roleMenuService;
    private final AdminService adminService;
    private final AuthClientService authClientService;

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

            lambdaQuery.in(Menu::getId, menuIds);
        }

        menus = menuService.list(lambdaQuery.isNull(Menu::getDeleteTime));

        TreeNodeConfig config = new TreeNodeConfig();
        config.setWeightKey("sort");
        config.setDeep(3);
        config.setParentIdKey("pid");

        return TreeUtil.build(menus, "0", config, (node, treeNode) -> {
            treeNode.setId(String.valueOf(node.getId()));
            treeNode.setParentId(String.valueOf(node.getPid()));
            treeNode.putExtra("title", node.getTitle());
            treeNode.putExtra("path", node.getPath());
            treeNode.putExtra("key", node.getKey());
            treeNode.putExtra("icon", node.getIcon());
            treeNode.putExtra("type", node.getType().getValue());
            treeNode.putExtra("sort", node.getSort());
        });
    }

    @Override
    public AdminInfo profile(AppUser user) {
        Admin admin = adminService.getById(user.getId());
        if (!admin.getStatus() || !ObjectUtils.isEmpty(admin.getDeleteTime())) {
            throw new AppException(RCode.UNAUTHORIZED_ERROR);
        }

        return new AdminInfo(admin.getUsername(), admin.getPhone());
    }

    @Override
    public void updateProfile(PasswordIn param, AppUser user) {
        Admin admin = adminService.getById(user.getId());
        AuthClient client = authClientService.getOne(Wrappers.<AuthClient>lambdaQuery()
                .eq(AuthClient::getClientId, param.getClientId())
                .eq(AuthClient::getClientSecret, param.getClientSecret())
                .isNull(AuthClient::getDeleteTime)
        );
        Assert.isTrue(admin.getPassword().equals(DigestUtils.md5Hex(RsaUtil.decrypt(client.getPrivateKey(), param.getPassword()))), "原始密码不正确");
        String newPassword = RsaUtil.decrypt(client.getPrivateKey(), param.getNewPassword());
        Assert.hasText(newPassword, "新密码不符合安全要求");

        admin.setPassword(DigestUtils.md5Hex(newPassword));
        admin.setUpdateTime(LocalDateTime.now());

        adminService.updateById(admin);
    }
}
