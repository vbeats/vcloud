package com.codestepfish.admin.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codestepfish.admin.dto.lov.LovQueryIn;
import com.codestepfish.admin.entity.Lov;
import com.codestepfish.admin.entity.LovCategory;
import com.codestepfish.admin.service.LovCategoryService;
import com.codestepfish.admin.service.LovService;
import com.codestepfish.core.result.PageOut;
import com.codestepfish.redis.util.LovUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/lov")
@SaCheckRole(value = {"super_admin"})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LovController {

    private final LovCategoryService lovCategoryService;
    private final LovService lovService;

    @GetMapping("/listCategory")
    public PageOut<List<LovCategory>> listCategory(LovQueryIn param) {

        Page<LovCategory> categoryPage = lovCategoryService.listCategory(new Page<>(param.getCurrent(), param.getPageSize()), param.getTenantId(), param.getCategory());
        return new PageOut<>(categoryPage.getTotal(), categoryPage.getRecords());
    }

    @GetMapping("/listLov")
    public PageOut<List<Lov>> listLov(LovQueryIn param) {
        LambdaQueryWrapper<Lov> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Lov::getLovCategoryId, param.getLovCategoryId());

        if (StringUtils.hasText(param.getKey())) {
            queryWrapper.eq(Lov::getKey, param.getKey());
        }

        Page<Lov> lovPage = lovService.page(new Page<>(param.getCurrent(), param.getPageSize()), queryWrapper);
        return new PageOut<>(lovPage.getTotal(), lovPage.getRecords());
    }

    @PostMapping("/addCategory")
    public void addCategory(@RequestBody LovCategory param) {
        LovCategory exist = lovCategoryService.getOne(Wrappers.<LovCategory>lambdaQuery().eq(LovCategory::getTenantId, param.getTenantId()).eq(LovCategory::getCategory, param.getCategory()));
        Assert.isNull(exist, "分组已存在");

        lovCategoryService.save(param);
    }

    @PostMapping("/addLov")
    public void addLov(@RequestBody Lov param) {
        LovCategory lovCategory = lovCategoryService.getById(param.getLovCategoryId());
        Lov exist = lovService.getOne(Wrappers.<Lov>lambdaQuery().eq(Lov::getLovCategoryId, param.getLovCategoryId()).eq(Lov::getKey, param.getKey()));
        Assert.isNull(exist, "值集已存在");

        lovService.save(param);
        LovUtil.set(lovCategory.getTenantId(), lovCategory.getCategory(), param.getKey(), param.getValue());
    }

    @PostMapping("/updateCategory")
    public void updateCategory(@RequestBody LovCategory param) {
        LovCategory exist = lovCategoryService.getOne(Wrappers.<LovCategory>lambdaQuery().eq(LovCategory::getTenantId, param.getTenantId()).eq(LovCategory::getCategory, param.getCategory()));
        Assert.isTrue(ObjectUtils.isEmpty(exist) || exist.getId().equals(param.getId()), "分组已存在");

        lovCategoryService.updateById(param);
        // cache 删除此分组 重新set cache
        LovUtil.deleteCategory(param.getTenantId(), param.getCategory());
        List<Lov> lovs = lovService.list(Wrappers.<Lov>lambdaQuery().eq(Lov::getLovCategoryId, param.getId()));
        if (!CollectionUtils.isEmpty(lovs)) {
            lovs.forEach(lov -> LovUtil.set(param.getTenantId(), param.getCategory(), lov.getKey(), lov.getValue()));
        }
    }

    @PostMapping("/updateLov")
    public void updateLov(@RequestBody Lov param) {
        Lov lov = lovService.getById(param.getId());
        LovCategory lovCategory = lovCategoryService.getById(lov.getLovCategoryId());

        lov.setKey(param.getKey());
        lov.setValue(param.getValue());
        lov.setRemark(param.getRemark());

        lovService.updateById(lov);
        LovUtil.set(lovCategory.getTenantId(), lovCategory.getCategory(), param.getKey(), param.getValue());
    }

    @PostMapping("/deleteLov")
    public void deleteLov(@RequestBody Lov param) {
        Lov lov = lovService.getById(param.getId());
        LovCategory lovCategory = lovCategoryService.getById(lov.getLovCategoryId());
        lovService.removeById(lov);
        LovUtil.delete(lovCategory.getTenantId(), lovCategory.getCategory(), lov.getKey());
    }

    @PostMapping("/deleteCategory")
    public void deleteCategory(@RequestBody LovCategory param) {
        lovCategoryService.removeById(param);
        LovUtil.deleteCategory(param.getTenantId(), param.getCategory());
        // cache 删除此分组下所有值集
        lovService.remove(Wrappers.<Lov>lambdaQuery().eq(Lov::getLovCategoryId, param.getId()));
        LovUtil.deleteCategory(param.getTenantId(), param.getCategory());
    }
}
