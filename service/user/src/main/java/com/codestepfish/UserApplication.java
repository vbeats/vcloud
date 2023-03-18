package com.codestepfish;

import com.codestepfish.core.feign.FeignConfig;
import com.dtp.core.spring.EnableDynamicTp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableDynamicTp
@SpringBootApplication
@EnableFeignClients(defaultConfiguration = FeignConfig.class)
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
