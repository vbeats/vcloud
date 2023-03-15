package com.codestepfish.admin.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codestepfish.admin.dto.lov.LovQueryIn;
import com.codestepfish.admin.entity.Lov;
import com.codestepfish.admin.entity.LovCategory;
import com.codestepfish.admin.entity.LovDefault;
import com.codestepfish.admin.service.LovCategoryService;
import com.codestepfish.admin.service.LovDefaultService;
import com.codestepfish.admin.service.LovService;
import com.codestepfish.core.constant.auth.AuthConstant;
import com.codestepfish.core.result.PageOut;
import com.codestepfish.redis.constant.LovConstants;
import com.codestepfish.redis.constant.RedisConstants;
import com.codestepfish.redis.util.LovUtil;
import com.codestepfish.redis.util.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/lov")
@SaCheckRole(value = {AuthConstant.SUPER_ADMIN})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LovController {

    private final LovCategoryService lovCategoryService;
    private final LovService lovService;
    private final LovDefaultService lovDefaultService;
    private final RedisService redisService;

    @GetMapping("/listCategory")
    public PageOut<List<LovCategory>> listCategory(LovQueryIn param) {

        Page<LovCategory> categoryPage = lovCategoryService.listCategory(new Page<>(param.getCurrent(), param.getPageSize()), param.getMerchantId(), param.getCategory());
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
        LovCategory exist = lovCategoryService.getOne(Wrappers.<LovCategory>lambdaQuery().eq(LovCategory::getMerchantId, param.getMerchantId()).eq(LovCategory::getCategory, param.getCategory()));
        Assert.isNull(exist, "分组已存在");

        lovCategoryService.save(param);
    }

    @PostMapping("/addLov")
    public void addLov(@RequestBody Lov param) {
        LovCategory lovCategory = lovCategoryService.getById(param.getLovCategoryId());
        Lov exist = lovService.getOne(Wrappers.<Lov>lambdaQuery().eq(Lov::getLovCategoryId, param.getLovCategoryId()).eq(Lov::getKey, param.getKey()));
        Assert.isNull(exist, "值集已存在");

        lovService.save(param);
        LovUtil.set(lovCategory.getMerchantId(), lovCategory.getCategory(), param.getKey(), param.getValue());
        setWechatAppidAndMerchantIdCache(param, lovCategory);
    }

    @PostMapping("/updateCategory")
    public void updateCategory(@RequestBody LovCategory param) {
        LovCategory exist = lovCategoryService.getOne(Wrappers.<LovCategory>lambdaQuery().eq(LovCategory::getMerchantId, param.getMerchantId()).eq(LovCategory::getCategory, param.getCategory()));
        Assert.isTrue(ObjectUtils.isEmpty(exist) || exist.getId().equals(param.getId()), "分组已存在");

        // 只允许更新备注信息
        LovCategory lovCategory = lovCategoryService.getById(param.getId());
        lovCategory.setRemark(param.getRemark());
        lovCategoryService.updateById(lovCategory);
    }

    @PostMapping("/updateLov")
    public void updateLov(@RequestBody Lov param) {
        Lov lov = lovService.getById(param.getId());
        LovCategory lovCategory = lovCategoryService.getById(lov.getLovCategoryId());

        lov.setKey(param.getKey());
        lov.setValue(param.getValue());
        lov.setRemark(param.getRemark());

        lovService.updateById(lov);
        LovUtil.set(lovCategory.getMerchantId(), lovCategory.getCategory(), param.getKey(), param.getValue());
        setWechatAppidAndMerchantIdCache(param, lovCategory);
    }

    /**
     * 设置 appid ---> merchantId 关联关系 cache
     *
     * @param lov
     * @param lovCategory
     */
    private void setWechatAppidAndMerchantIdCache(Lov lov, LovCategory lovCategory) {
        // 微信 appid --> merchantId
        if (LovConstants.WECHAT_CATEGORY.equals(lovCategory.getCategory()) && (Arrays.asList(LovConstants.WX_MA_APPID, LovConstants.WX_MP_APPID, LovConstants.WX_CP_APPID).contains(lov.getKey()))) {
            redisService.set(String.format(RedisConstants.WX_APPID_BUCKET, lov.getValue()), lovCategory.getMerchantId());
        }
    }

    @PostMapping("/deleteLov")
    public void deleteLov(@RequestBody Lov param) {
        Lov lov = lovService.getById(param.getId());
        LovCategory lovCategory = lovCategoryService.getById(lov.getLovCategoryId());
        lovService.removeById(lov);
        LovUtil.delete(lovCategory.getMerchantId(), lovCategory.getCategory(), lov.getKey());
        // 微信 appid --> merchantId
        if (LovConstants.WECHAT_CATEGORY.equals(lovCategory.getCategory()) && (Arrays.asList(LovConstants.WX_MA_APPID, LovConstants.WX_MP_APPID, LovConstants.WX_CP_APPID).contains(lov.getKey()))) {
            redisService.getAndDelete(String.format(RedisConstants.WX_APPID_BUCKET, lov.getValue()));
        }
    }

    @PostMapping("/deleteCategory")
    public void deleteCategory(@RequestBody LovCategory param) {
        // 存在值集配置的不能删除
        List<Lov> lovs = lovService.list(Wrappers.<Lov>lambdaQuery().eq(Lov::getLovCategoryId, param.getId()));
        Assert.isTrue(CollectionUtils.isEmpty(lovs), "请先删除值集配置");
        lovCategoryService.removeById(param.getId());
    }

    //*************************值集默认配置*********************************
    @GetMapping("/listLovDefault")
    public PageOut<List<LovDefault>> listLovDefault(LovQueryIn param) {
        LambdaQueryWrapper<LovDefault> queryWrapper = Wrappers.lambdaQuery();

        if (StringUtils.hasText(param.getKey())) {
            queryWrapper.eq(LovDefault::getKey, param.getKey());
        }

        Page<LovDefault> lovPage = lovDefaultService.page(new Page<>(param.getCurrent(), param.getPageSize()), queryWrapper);
        return new PageOut<>(lovPage.getTotal(), lovPage.getRecords());
    }

    @PostMapping("/addLovDefault")
    public void addLovDefault(@RequestBody LovDefault param) {
        LovDefault exist = lovDefaultService.getOne(Wrappers.<LovDefault>lambdaQuery().eq(LovDefault::getKey, param.getKey()));
        Assert.isNull(exist, "值集已存在");

        lovDefaultService.save(param);
        LovUtil.setDefault(param.getKey(), param.getValue());
    }

    @PostMapping("/updateLovDefault")
    public void updateLovDefault(@RequestBody LovDefault param) {
        LovDefault lovDefault = lovDefaultService.getById(param.getId());

        lovDefault.setValue(param.getValue());
        lovDefault.setRemark(param.getRemark());

        lovDefaultService.updateById(lovDefault);
        LovUtil.setDefault(lovDefault.getKey(), param.getValue());
    }

    @PostMapping("/deleteLovDefault")
    public void deleteLovDefault(@RequestBody LovDefault param) {
        LovDefault lovDefault = lovDefaultService.getById(param.getId());
        lovDefaultService.removeById(lovDefault);
        LovUtil.deleteDefault(lovDefault.getKey());
    }
}
