package com.codestepfish.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codestepfish.admin.entity.Admin;
import com.codestepfish.admin.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class AdminService extends ServiceImpl<AdminMapper, Admin> implements IService<Admin> {

    private final AdminMapper adminMapper;

    public Admin getAdminByAccount(String account, String password, String tenantCode) {
        return adminMapper.getAdminByAccount(account, password, tenantCode);
    }

    public Page<Admin> listAdmins(Page<Admin> page, String tenantCode, String account, String phone) {
        return adminMapper.listAdmins(page, tenantCode, account, phone);
    }
}
