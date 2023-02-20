package com.codestepfish.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codestepfish.admin.entity.LovCategory;
import org.apache.ibatis.annotations.Param;

public interface LovCategoryMapper extends BaseMapper<LovCategory> {
    Page<LovCategory> listCategory(Page<LovCategory> page, @Param("tenant_id") Long tenantId, @Param("category") String category);
}
