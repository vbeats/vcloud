package com.codestepfish.admin.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codestepfish.admin.dto.admin.AdminQueryIn;
import com.codestepfish.common.constant.redis.CacheEnum;
import com.codestepfish.common.result.PageOut;
import com.codestepfish.datasource.entity.Admin;
import com.codestepfish.datasource.entity.Tenant;
import com.codestepfish.datasource.service.AdminService;
import com.codestepfish.datasource.service.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/admin")
@SaCheckRole(value = {"super_admin"})
public class AdminController {

    private final AdminService adminService;
    private final TenantService tenantService;

    @PostMapping("/list")
    public PageOut<List<Admin>> list(@RequestBody AdminQueryIn param) {
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
        Admin existAccount = adminService.getOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getTenantCode, tenant.getCode()).eq(Admin::getAccount, param.getAccount()));
        Assert.isNull(existAccount, "账号已存在");
        Assert.hasText(param.getPassword(), "密码错误");

        Admin admin = new Admin();
        admin.setTenantCode(tenant.getCode());
        admin.setAccount(param.getAccount().trim());
        admin.setUsername(param.getUsername().trim());
        if (StringUtils.hasText(param.getPhone())) {
            Admin existPhone = adminService.getOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getTenantCode, tenant.getCode()).eq(Admin::getPhone, param.getPhone()));
            Assert.isNull(existPhone, "手机号已存在");
            admin.setPhone(param.getPhone().trim());
        } else {
            admin.setPhone("");
        }
        admin.setPassword(DigestUtils.md5Hex(param.getPassword()));
        admin.setStatus(true);
        if (!ObjectUtils.isEmpty(param.getRoleId())) {
            admin.setRoleId(param.getRoleId());
        }
        admin.setCreateTime(LocalDateTime.now());
        adminService.save(admin);
    }

    @PostMapping("/update")
    @CacheEvict(cacheNames = {CacheEnum.ADMIN_CACHE, CacheEnum.ROLE_CACHE, CacheEnum.PERMISSION_CACHE}, key = "#param.id")
    public void update(@RequestBody Admin param) {
        Admin admin = adminService.getById(param.getId());

        admin.setUsername(StringUtils.hasText(param.getUsername()) ? param.getUsername().trim() : "");
        if (StringUtils.hasText(param.getPhone())) {
            Admin exist = adminService.getOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getTenantCode, admin.getTenantCode()).eq(Admin::getPhone, param.getPhone().trim()));
            Assert.isTrue(ObjectUtils.isEmpty(exist) || exist.getId().equals(admin.getId()), "手机号已存在");
        } else {
            admin.setPhone("");
        }
        admin.setRoleId(param.getRoleId());
        if (StringUtils.hasText(param.getPassword())) {
            admin.setPassword(DigestUtils.md5Hex(param.getPassword()));
        }
        admin.setUpdateTime(LocalDateTime.now());
        adminService.updateById(admin);
    }

    @PostMapping("/delete")
    @CacheEvict(cacheNames = {CacheEnum.ADMIN_CACHE, CacheEnum.ROLE_CACHE, CacheEnum.PERMISSION_CACHE}, key = "#param.id")
    public void delete(@RequestBody Admin param) {
        Admin admin = adminService.getById(param.getId());
        admin.setDeleteTime(LocalDateTime.now());
        adminService.updateById(admin);
    }

    @PostMapping("/block")
    @CacheEvict(cacheNames = {CacheEnum.ADMIN_CACHE, CacheEnum.ROLE_CACHE, CacheEnum.PERMISSION_CACHE}, allEntries = true)
    public void block(@RequestBody List<Admin> params) {
        toggleAdminStatus(params, false);
    }

    @PostMapping("/unblock")
    @CacheEvict(cacheNames = {CacheEnum.ADMIN_CACHE, CacheEnum.ROLE_CACHE, CacheEnum.PERMISSION_CACHE}, allEntries = true)
    public void unBlock(@RequestBody List<Admin> params) {
        toggleAdminStatus(params, true);
    }

    @PostMapping("/resetPwd")
    public void resetPwd(@RequestBody Admin param) {
        Admin admin = adminService.getById(param.getId());
        admin.setPassword(DigestUtils.md5Hex("123456"));
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

}
