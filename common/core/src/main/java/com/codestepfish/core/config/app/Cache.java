package com.codestepfish.core.config.app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cache {
    private String cacheName;  // redis cache 名
    private Duration ttl = Duration.ofMinutes(30L);           // 最大生存时间   默认30分钟
    private Duration maxIdleTime = Duration.ofMinutes(20L);   // 最大空闲时间  默认20分钟
    private Integer maxSize = 10;   // cache Map最大元素个数  默认10个
}
