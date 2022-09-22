package com.codestepfish.admin.controller;

import com.codestepfish.admin.feign.TestFeignClient;
import com.codestepfish.common.model.AppUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestController {

    private final TestFeignClient testFeignClient;

    @PostMapping("/tt")
    public AppUser test(AppUser user) {
        return testFeignClient.feignTest(user).getData();
    }
}
