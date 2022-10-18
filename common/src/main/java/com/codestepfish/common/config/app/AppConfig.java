package com.codestepfish.common.config.app;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "vcloud")
@Getter
@Setter
@Slf4j
public class AppConfig {

    private List<String> skipUrls; //  白名单 无需认证 放行uri

    private List<Cache> caches; // redis cache
}
