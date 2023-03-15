package com.codestepfish.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codestepfish.admin.entity.LovCategory;
import org.apache.ibatis.annotations.Param;

public interface LovCategoryMapper extends BaseMapper<LovCategory> {
    Page<LovCategory> listCategory(Page<LovCategory> page, @Param("merchant_id") Long merchantId, @Param("category") String category);
}
