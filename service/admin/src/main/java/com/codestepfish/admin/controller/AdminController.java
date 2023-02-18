package com.codestepfish.admin.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codestepfish.admin.dto.admin.AdminQueryIn;
import com.codestepfish.admin.dto.admin.AssignAdminRolesIn;
import com.codestepfish.admin.entity.Admin;
import com.codestepfish.admin.entity.AdminRole;
import com.codestepfish.admin.entity.Role;
import com.codestepfish.admin.entity.Tenant;
import com.codestepfish.admin.service.AdminRoleService;
import com.codestepfish.admin.service.AdminService;
import com.codestepfish.admin.service.PermissionService;
import com.codestepfish.admin.service.TenantService;
import com.codestepfish.core.model.AppUser;
import com.codestepfish.core.result.PageOut;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/admin")
@SaCheckRole(value = {"super_admin"})
public class AdminController {

    private final AdminService adminService;
    private final TenantService tenantService;
    private final AdminRoleService adminRoleService;
    private final PermissionService permissionService;

    @GetMapping("/list")
    public PageOut<List<Admin>> list(AdminQueryIn param) {

        Tenant t = tenantService.getById(ObjectUtils.isEmpty(param.getTenantId()) ? Long.valueOf(String.valueOf(StpUtil.getExtra("tenantId"))) : param.getTenantId());
        Page<Admin> pages = adminService.listAdmins(new Page<>(param.getCurrent(), param.getPageSize()), t.getCode(), param.getAccount(), param.getPhone());

        PageOut<List<Admin>> out = new PageOut<>();
        out.setTotal(pages.getTotal());
        out.setRows(pages.getRecords());

        return out;
    }

    @PostMapping("/add")
    public void add(@RequestBody Admin param) {
        Tenant tenant = tenantService.getById(param.getTenantId());
        Admin existAccount = adminService.getOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getTenantId, tenant.getId()).eq(Admin::getAccount, param.getAccount()));
        Assert.isNull(existAccount, "账号已存在");
        Assert.hasText(param.getPassword(), "密码错误");

        Admin admin = new Admin();
        admin.setTenantId(tenant.getId());
        admin.setAccount(param.getAccount());
        admin.setNickName(param.getNickName());
        if (StringUtils.hasText(param.getPhone())) {
            Admin existPhone = adminService.getOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getTenantId, tenant.getId()).eq(Admin::getPhone, param.getPhone()));
            Assert.isNull(existPhone, "手机号已存在");
            admin.setPhone(param.getPhone());
        } else {
            admin.setPhone("");
        }
        admin.setPassword(DigestUtils.md5Hex(param.getAccount() + "*" + param.getPassword()));
        admin.setStatus(true);
        admin.setDelFlag(false);
        admin.setCreateTime(LocalDateTime.now());
        adminService.save(admin);
    }

    @PostMapping("/update")
    public void update(@RequestBody Admin param) {
        Admin admin = adminService.getById(param.getId());

        if (StringUtils.hasText(param.getPhone())) {
            Admin exist = adminService.getOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getTenantId, admin.getTenantId()).eq(Admin::getPhone, param.getPhone().trim()));
            Assert.isTrue(ObjectUtils.isEmpty(exist) || exist.getId().equals(admin.getId()), "手机号已存在");
            admin.setPhone(param.getPhone());
        } else {
            admin.setPhone("");
        }
        if (StringUtils.hasText(param.getPassword())) {
            admin.setPassword(DigestUtils.md5Hex(admin.getAccount() + "*" + param.getPassword()));
        }
        admin.setUpdateTime(LocalDateTime.now());
        adminService.updateById(admin);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody Admin param) {
        Assert.isTrue(!param.getId().equals(1L), "此用户不可删除");
        Admin admin = adminService.getById(param.getId());
        admin.setDelFlag(true);
        admin.setUpdateTime(LocalDateTime.now());
        adminService.updateById(admin);
    }

    @PostMapping("/block")
    public void block(@RequestBody List<Admin> params) {
        toggleAdminStatus(params, false);
    }

    @PostMapping("/unblock")
    public void unBlock(@RequestBody List<Admin> params) {
        toggleAdminStatus(params, true);
    }

    @PostMapping("/resetPwd")
    public void resetPwd(@RequestBody Admin param) {
        Admin admin = adminService.getById(param.getId());
        admin.setPassword(DigestUtils.md5Hex(admin.getAccount() + "*123456"));
        admin.setUpdateTime(LocalDateTime.now());
        adminService.updateById(admin);
    }

    private void toggleAdminStatus(List<Admin> params, boolean status) {

        List<Admin> admins = params.stream().map(e -> {
            Admin admin = adminService.getById(e.getId());
            admin.setStatus(status);
            admin.setUpdateTime(LocalDateTime.now());
            return admin;
        }).collect(Collectors.toList());

        adminService.updateBatchById(admins);
    }

    @PostMapping("/assignAdminRoles")
    public void assignAdminRoles(@RequestBody AssignAdminRolesIn param) {
        Admin admin = adminService.getById(param.getAdminId());
        Assert.isTrue(!admin.getId().equals(1L), "此用户禁止修改");

        if (CollectionUtils.isEmpty(param.getRoles())) {
            adminRoleService.remove(Wrappers.<AdminRole>lambdaQuery().eq(AdminRole::getAdminId, param.getAdminId()));
            return;
        }

        Set<Long> rIds = param.getRoles().stream().map(Role::getId).collect(Collectors.toSet());

        // 此用户已分配角色
        List<AdminRole> adminRoles = adminRoleService.list(Wrappers.<AdminRole>lambdaQuery().eq(AdminRole::getAdminId, param.getAdminId()));
        Set<Long> roleIds = adminRoles.stream().map(e -> e.getRoleId()).collect(Collectors.toSet());

        // 需要删除的角色
        Sets.SetView<Long> needRemoveIds = Sets.difference(roleIds, rIds);
        // 需要新分配的角色
        Sets.SetView<Long> needAddIds = Sets.difference(rIds, roleIds);

        if (!CollectionUtils.isEmpty(needRemoveIds)) {
            adminRoleService.remove(Wrappers.<AdminRole>lambdaQuery().eq(AdminRole::getAdminId, param.getAdminId()).in(AdminRole::getRoleId, needRemoveIds));
        }

        if (!CollectionUtils.isEmpty(needAddIds)) {
            List<AdminRole> ars = needAddIds.stream().map(e -> new AdminRole(null, param.getAdminId(), e)).collect(Collectors.toList());
            adminRoleService.saveBatch(ars);
        }
    }

    @SaIgnore
    @GetMapping("/info")
    public AppUser getAdminInfo(@RequestParam("account") String account, @RequestParam("password") String password, @RequestParam("tenant_code") String tenantCode) {
        Admin admin = adminService.getAdminByAccount(account, password, tenantCode);
        Assert.notNull(admin, "账号/密码错误");
        Assert.isTrue(admin.getStatus(), "账号已被禁用");
        Assert.isTrue(!admin.getDelFlag(), "账号已被删除");

        AppUser appUser = new AppUser();
        appUser.setId(admin.getId());
        appUser.setTenantId(admin.getTenantId());
        appUser.setAccount(admin.getAccount());
        appUser.setNickName(admin.getNickName());
        appUser.setPhone(admin.getPhone());

        // 账号角色 & 菜单权限
        if (admin.isSuperAdmin()) {
            appUser.setRoles(Collections.singleton("*"));
            appUser.setPermissions(Collections.singleton("*"));
        } else {
            Set<String> roles = permissionService.getRoles(admin.getId());
            Set<String> permission = permissionService.getPermissions(admin.getId());

            roles.add("admin");  // 管理员默认都有admin权限

            appUser.setRoles(roles);
            appUser.setPermissions(permission);
        }

        return appUser;
    }

}
