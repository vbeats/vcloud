package com.codestepfish.datasource.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codestepfish.datasource.entity.User;
import com.codestepfish.datasource.entity.UserWxMa;
import org.apache.ibatis.annotations.Param;

public interface UserWxMaMapper extends BaseMapper<UserWxMa> {
    User findByTenantIdAndOpenId(@Param("tenant_id") Long tenantId, @Param("open_config_id") Long openConfigId, @Param("openid") String openid);
}
