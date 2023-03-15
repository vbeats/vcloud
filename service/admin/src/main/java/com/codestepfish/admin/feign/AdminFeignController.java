package com.codestepfish.admin.feign;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codestepfish.admin.entity.*;
import com.codestepfish.admin.service.*;
import com.codestepfish.core.model.AppUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/admin")
public class AdminFeignController {

    private final AdminService adminService;
    private final MerchantService merchantService;
    private final RoleService roleService;
    private final RoleMenuService roleMenuService;
    private final MenuService menuService;

    @GetMapping("/info")
    public AppUser getAdminInfo(@RequestParam("account") String account, @RequestParam("password") String password, @RequestParam("merchant_code") String merchantCode) {
        Admin admin = adminService.getAdminByAccount(account, password, merchantCode);
        Assert.notNull(admin, "账号/密码错误");
        Assert.isTrue(admin.getStatus(), "账号已被禁用");
        Assert.isTrue(!admin.getDelFlag(), "账号已被删除");

        AppUser appUser = new AppUser();
        appUser.setId(admin.getId());
        appUser.setRoleId(admin.getRoleId());
        appUser.setMerchantId(admin.getMerchantId());
        appUser.setMerchantCode(admin.getMerchantCode());
        appUser.setMerchantName(admin.getMerchantName());
        appUser.setAccount(admin.getAccount());
        appUser.setNickName(admin.getNickName());
        appUser.setPhone(admin.getPhone());
        appUser.setIsSuperAdmin(admin.isSuperAdmin());

        // 账号角色 & 菜单权限
        if (admin.isSuperAdmin()) {
            appUser.setRoles(Collections.singleton("*"));
            appUser.setPermissions(Collections.singleton("*"));
        } else {
            Role role = roleService.getById(admin.getRoleId());

            appUser.setRoles(Set.of("admin", role.getAction()));  // role

            // permission
            List<Long> menuIds = roleMenuService.list(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, admin.getRoleId()))
                    .stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
            appUser.setPermissions(CollectionUtils.isEmpty(menuIds) ? Collections.emptySet() : menuService.listByIds(menuIds).stream().map(Menu::getPermission).collect(Collectors.toSet()));

            // dataScope
            List<Merchant> merchants = merchantService.list(Wrappers.<Merchant>lambdaQuery().eq(Merchant::getId, admin.getMerchantId()).or().apply("find_in_set({0},pids)", admin.getMerchantId()));
            appUser.setDataScopes(merchants.stream().map(Merchant::getId).collect(Collectors.toSet()));
        }

        return appUser;
    }
}
