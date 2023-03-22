package com.codestepfish.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codestepfish.admin.entity.LovCategory;
import com.codestepfish.admin.mapper.LovCategoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LovCategoryService extends ServiceImpl<LovCategoryMapper, LovCategory> implements IService<LovCategory> {

    private final LovCategoryMapper lovCategoryMapper;

    public Page<LovCategory> listCategory(Page<LovCategory> page, Long tenantId, String category) {
        return lovCategoryMapper.listCategory(page, tenantId, category);
    }
}
