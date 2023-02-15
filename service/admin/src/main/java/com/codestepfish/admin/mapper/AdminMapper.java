package com.codestepfish.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codestepfish.admin.entity.Admin;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper extends BaseMapper<Admin> {

    Admin getAdminByAccount(@Param("account") String account, @Param("password") String password, @Param("tenant_code") String tenantCode);
}
