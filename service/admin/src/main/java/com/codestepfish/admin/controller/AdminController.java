package com.codestepfish.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codestepfish.admin.dto.admin.AdminQueryIn;
import com.codestepfish.admin.dto.admin.AdminVo;
import com.codestepfish.admin.service.AdminService;
import com.codestepfish.admin.service.AuthClientService;
import com.codestepfish.admin.service.TenantService;
import com.codestepfish.common.model.AppUser;
import com.codestepfish.common.result.PageOut;
import com.codestepfish.common.util.RsaUtil;
import com.codestepfish.core.annotation.PreAuth;
import com.codestepfish.datasource.entity.Admin;
import com.codestepfish.datasource.entity.AuthClient;
import com.codestepfish.datasource.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/admin")
@PreAuth
public class AdminController {

    private final AdminService adminService;
    private final TenantService tenantService;
    private final AuthClientService authClientService;

    @PostMapping("/list")
    public PageOut<List<AdminVo>> list(@RequestBody AdminQueryIn param, AppUser user) {
        Tenant t = tenantService.getById(ObjectUtils.isEmpty(param.getTenantId()) ? user.getTenantId() : param.getTenantId());
        Page<AdminVo> pages = adminService.listAdmins(new Page<AdminVo>(param.getCurrent(), param.getPageSize()), t.getCode(), param.getAccount(), param.getPhone());

        PageOut<List<AdminVo>> out = new PageOut<>();
        out.setTotal(pages.getTotal());
        out.setRows(pages.getRecords());

        return out;
    }

    @PostMapping("/add")
    public void add(@RequestBody AdminVo param) {
        Tenant tenant = tenantService.getById(param.getTenantId());
        Admin existAccount = adminService.getOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getTenantCode, tenant.getCode()).eq(Admin::getAccount, param.getAccount()));
        Assert.isNull(existAccount, "账号已存在");
        Admin existPhone = adminService.getOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getTenantCode, tenant.getCode()).eq(Admin::getPhone, param.getPhone()));
        Assert.isNull(existPhone, "手机号已存在");

        Admin admin = new Admin();
        admin.setTenantCode(tenant.getCode());
        admin.setAccount(param.getAccount());
        admin.setUsername(param.getUsername());
        String password = RsaUtil.decrypt(getAuthClient(param.getClientId(), param.getClientSecret()).getPrivateKey(), param.getPassword());
        Assert.hasText(password, "密码错误");
        admin.setPassword(DigestUtils.md5Hex(password));
        admin.setStatus(true);
        admin.setCreateTime(LocalDateTime.now());
        adminService.save(admin);
    }

    private AuthClient getAuthClient(String clientId, String secret) {
        return authClientService.getOne(Wrappers.<AuthClient>lambdaQuery().eq(AuthClient::getClientId, clientId).eq(AuthClient::getClientSecret, secret).isNull(AuthClient::getDeleteTime));
    }
}
