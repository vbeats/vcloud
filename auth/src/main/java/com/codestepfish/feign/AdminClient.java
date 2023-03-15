package com.codestepfish.feign;

import com.codestepfish.auth.dto.Merchant;
import com.codestepfish.core.feign.DefaultFallbackFactory;
import com.codestepfish.core.model.AppUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "admin", fallbackFactory = DefaultFallbackFactory.class)
public interface AdminClient {

    // 管理员认证
    @GetMapping("/admin/info")
    AppUser getAdminInfo(@RequestParam("account") String account, @RequestParam("password") String password, @RequestParam("merchant_code") String merchantCode);

    @GetMapping("/merchant/getMerchantByCode")
    Merchant getMerchantByCode(@RequestParam("merchant_code") String merchantCode);

    @GetMapping("/merchant/getMerchantById")
    Merchant getMerchantById(@RequestParam("merchant_id") Long merchantId);
}
