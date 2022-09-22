package com.codestepfish.test;

import com.codestepfish.common.model.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @PostMapping("/feignTest")
    public AppUser feignTest(AppUser user) {
        return user;
    }
}
