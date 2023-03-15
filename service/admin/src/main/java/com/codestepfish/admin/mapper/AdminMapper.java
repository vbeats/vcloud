package com.codestepfish.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codestepfish.admin.entity.Admin;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper extends BaseMapper<Admin> {

    Admin getAdminByAccount(@Param("account") String account, @Param("password") String password, @Param("merchant_code") String merchantCode);

    Page<Admin> listAdmins(Page<Admin> page, @Param("merchant_code") String merchantCode, @Param("account") String account, @Param("phone") String phone);
}
