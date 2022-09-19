package com.codestepfish.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codestepfish.admin.dto.admin.AdminVo;
import com.codestepfish.datasource.entity.Admin;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper extends BaseMapper<Admin> {
    Page<AdminVo> listAdmins(Page<AdminVo> page, @Param("tenant_code") String tenantCode, @Param("account") String account, @Param("phone") String phone);
}
