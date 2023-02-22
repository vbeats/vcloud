package com.codestepfish.feign;

import com.codestepfish.core.feign.DefaultFallbackFactory;
import com.codestepfish.core.model.AppUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user", fallbackFactory = DefaultFallbackFactory.class)
public interface UserClient {

    // C端用户认证
    @GetMapping("/user/info")
    AppUser getUserInfo(@RequestParam("code") String code, @RequestParam("appid") String appid);
}
