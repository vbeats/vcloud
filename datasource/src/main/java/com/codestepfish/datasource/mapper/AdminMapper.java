package com.codestepfish.datasource.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codestepfish.datasource.entity.Admin;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper extends BaseMapper<Admin> {

    Page<Admin> listAdmins(Page<Admin> page, @Param("tenant_code") String tenantCode, @Param("account") String account, @Param("phone") String phone);
}
