package com.codestepfish.admin.feign;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codestepfish.admin.entity.Merchant;
import com.codestepfish.admin.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/merchant")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MerchantFeignController {

    private final MerchantService merchantService;

    @GetMapping("/getMerchantByCode")
    public Merchant getMerchantByCode(@RequestParam(value = "merchant_code") String merchantCode) {
        return merchantService.getOne(Wrappers.<Merchant>lambdaQuery().eq(Merchant::getCode, merchantCode));
    }

    @GetMapping("/getMerchantById")
    public Merchant getMerchantById(@RequestParam(value = "merchant_id") Long merchantId) {
        return merchantService.getById(merchantId);
    }
}
