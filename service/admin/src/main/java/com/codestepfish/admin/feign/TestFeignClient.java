package com.codestepfish.admin.feign;

import com.codestepfish.common.model.AppUser;
import com.codestepfish.common.result.R;
import com.codestepfish.core.feign.DefaultFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "test", fallbackFactory = DefaultFallbackFactory.class)
public interface TestFeignClient {

    @PostMapping("/test/feignTest")
    R<AppUser> feignTest(AppUser user);
}
