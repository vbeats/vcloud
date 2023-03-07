package com.codestepfish.admin.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codestepfish.admin.dto.admin.AdminQueryIn;
import com.codestepfish.admin.dto.admin.PasswordIn;
import com.codestepfish.admin.entity.Admin;
import com.codestepfish.admin.entity.Tenant;
import com.codestepfish.admin.service.AdminService;
import com.codestepfish.admin.service.TenantService;
import com.codestepfish.core.constant.auth.AuthConstant;
import com.codestepfish.core.result.PageOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final TenantService tenantService;

    @GetMapping("/list")
    @SaCheckRole(value = {AuthConstant.SUPER_ADMIN})
    public PageOut<List<Admin>> list(AdminQueryIn param) {

        Tenant t = tenantService.getById(ObjectUtils.isEmpty(param.getTenantId()) ? Long.valueOf(String.valueOf(StpUtil.getExtra("tenantId"))) : param.getTenantId());
        Page<Admin> pages = adminService.listAdmins(new Page<>(param.getCurrent(), param.getPageSize()), t.getCode(), param.getAccount(), param.getPhone());

        PageOut<List<Admin>> out = new PageOut<>();
        out.setTotal(pages.getTotal());
        out.setRows(pages.getRecords());

        return out;
    }

    @PostMapping("/add")
    @SaCheckRole(value = {AuthConstant.SUPER_ADMIN})
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
        admin.setPassword(DigestUtils.md5Hex(String.format(AuthConstant.PASSWORD_RULE, param.getAccount(), param.getPassword())));
        admin.setRoleId(param.getRoleId());
        admin.setStatus(true);
        admin.setDelFlag(false);
        admin.setCreateTime(LocalDateTime.now());
        adminService.save(admin);
    }

    @PostMapping("/update")
    @SaCheckRole(value = {AuthConstant.SUPER_ADMIN})
    public void update(@RequestBody Admin param) {
        Assert.isTrue(!param.getId().equals(1L), "此用户禁止操作");
        Admin admin = adminService.getById(param.getId());

        if (StringUtils.hasText(param.getPhone())) {
            Admin exist = adminService.getOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getTenantId, admin.getTenantId()).eq(Admin::getPhone, param.getPhone().trim()));
            Assert.isTrue(ObjectUtils.isEmpty(exist) || exist.getId().equals(admin.getId()), "手机号已存在");
            admin.setPhone(param.getPhone());
        } else {
            admin.setPhone("");
        }
        if (StringUtils.hasText(param.getPassword())) {
            admin.setPassword(DigestUtils.md5Hex(String.format(AuthConstant.PASSWORD_RULE, admin.getAccount(), param.getPassword())));
        }
        if (StringUtils.hasText(param.getNickName())) {
            admin.setNickName(param.getNickName());
        }
        admin.setRoleId(param.getRoleId());
        admin.setUpdateTime(LocalDateTime.now());
        adminService.updateById(admin);
    }

    @PostMapping("/delete")
    @SaCheckRole(value = {AuthConstant.SUPER_ADMIN})
    public void delete(@RequestBody Admin param) {
        Assert.isTrue(!param.getId().equals(1L), "此用户不可删除");
        Admin admin = adminService.getById(param.getId());
        admin.setDelFlag(true);
        admin.setUpdateTime(LocalDateTime.now());
        adminService.updateById(admin);
    }

    @PostMapping("/block")
    @SaCheckRole(value = {AuthConstant.SUPER_ADMIN})
    public void block(@RequestBody List<Admin> params) {
        toggleAdminStatus(params, false);
    }

    @PostMapping("/unblock")
    @SaCheckRole(value = {AuthConstant.SUPER_ADMIN})
    public void unBlock(@RequestBody List<Admin> params) {
        toggleAdminStatus(params, true);
    }

    @PostMapping("/resetPwd")
    @SaCheckRole(value = {AuthConstant.SUPER_ADMIN})
    public void resetPwd(@RequestBody Admin param) {
        Admin admin = adminService.getById(param.getId());
        Assert.isTrue(!admin.getId().equals(1L), "此用户禁止操作");
        admin.setPassword(DigestUtils.md5Hex(String.format(AuthConstant.PASSWORD_RULE, admin.getAccount(), AuthConstant.DEFAULT_PASSWORD)));
        admin.setUpdateTime(LocalDateTime.now());
        adminService.updateById(admin);
    }

    private void toggleAdminStatus(List<Admin> params, boolean status) {

        List<Admin> admins = params.stream().map(e -> {
            Admin admin = adminService.getById(e.getId());
            admin.setStatus(status);
            admin.setUpdateTime(LocalDateTime.now());

            // 踢掉用户
            if (!status) {
                StpUtil.logout(admin.getId());
            }

            return admin;
        }).collect(Collectors.toList());

        adminService.updateBatchById(admins);
    }

    @PostMapping("/updateProfile")
    public void updateProfile(@RequestBody PasswordIn param) {
        Admin admin = adminService.getById(StpUtil.getLoginIdAsLong());

        Assert.isTrue(admin.getPassword().equals(DigestUtils.md5Hex(String.format(AuthConstant.PASSWORD_RULE, admin.getAccount(), param.getPassword()))), "原始密码不正确");
        Assert.hasText(param.getNewPassword(), "新密码不符合安全要求");

        admin.setPassword(DigestUtils.md5Hex(String.format(AuthConstant.PASSWORD_RULE, admin.getAccount(), param.getNewPassword())));
        admin.setUpdateTime(LocalDateTime.now());

        adminService.updateById(admin);
    }
}
