package com.codestepfish.admin.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codestepfish.admin.dto.merchant.MerchantQueryIn;
import com.codestepfish.admin.entity.Merchant;
import com.codestepfish.admin.service.MerchantService;
import com.codestepfish.core.constant.auth.AuthConstant;
import com.codestepfish.core.result.PageOut;
import com.codestepfish.core.util.PidsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/merchant")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MerchantController {

    private final MerchantService merchantService;

    @SaCheckRole(value = {AuthConstant.SUPER_ADMIN})
    @GetMapping("/list")  // 所有商户
    public PageOut<List<Merchant>> list(MerchantQueryIn param) {
        if (StringUtils.hasText(param.getCode()) || StringUtils.hasText(param.getMerchantName())) {
            param.setPid(null);
        }
        Page<Merchant> pages = merchantService.listMerchant(new Page<>(param.getCurrent(), param.getPageSize()), param.getPid(), param.getCode(), param.getMerchantName());

        PageOut<List<Merchant>> out = new PageOut<>();

        out.setTotal(pages.getTotal());
        out.setRows(pages.getRecords());

        return out;
    }

    @GetMapping("/listMerchantTree")
    public List<Merchant> listMerchantTree() {
        Long id = StpUtil.getLoginIdAsLong();
        Long merchantId = Long.valueOf(String.valueOf(StpUtil.getExtra(AuthConstant.Extra.MERCHANT_ID)));
        if (id.equals(1L)) {
            merchantId = null;
        }

        return merchantService.listMerchantTree(merchantId);
    }

    @GetMapping("/sub")
    public List<Merchant> subMerchant(MerchantQueryIn param) {
        return merchantService.subMerchant(param.getPid());
    }

    @SaCheckRole(value = {AuthConstant.SUPER_ADMIN})
    @PostMapping("/add")
    public Merchant add(@RequestBody Merchant merchant) {
        String code = "M" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd")) + RandomUtil.randomNumbers(5);
        merchant.setCode(code);

        if (!merchant.getPid().equals(0L)) {
            Merchant pt = merchantService.getById(merchant.getPid());
            merchant.setPids(PidsUtil.appendPids(pt.getPids(), pt.getId()));
        }

        merchant.setCreateTime(LocalDateTime.now());
        merchantService.save(merchant);
        return merchant;
    }

    @SaCheckRole(value = {AuthConstant.SUPER_ADMIN})
    @PostMapping("/update")
    public void update(@RequestBody Merchant merchant) {
        Merchant m = merchantService.getById(merchant.getId());
        m.setMerchantName(merchant.getMerchantName());
        m.setRemark(merchant.getRemark());

        m.setUpdateTime(LocalDateTime.now());
        merchantService.updateById(m);
    }
}
