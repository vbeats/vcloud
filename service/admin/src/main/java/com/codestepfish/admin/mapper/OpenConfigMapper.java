package com.codestepfish.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codestepfish.admin.dto.open.OpenConfigVo;
import com.codestepfish.datasource.entity.OpenConfig;
import org.apache.ibatis.annotations.Param;

public interface OpenConfigMapper extends BaseMapper<OpenConfig> {
    Page<OpenConfigVo> listConfigs(Page<OpenConfigVo> page, @Param("tenant_id") Long tenantId, @Param("name") String name);
}
