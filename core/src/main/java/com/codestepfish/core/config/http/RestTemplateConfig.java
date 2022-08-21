package com.codestepfish.core.config.http;

import okhttp3.OkHttpClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    @LoadBalanced
    public RestTemplate restTemplate() {
        //需要其它额外的功能可以再这里配置

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .callTimeout(Duration.ofSeconds(10L))
                .connectTimeout(Duration.ofSeconds(10L)).readTimeout(Duration.ofSeconds(10L))
                .writeTimeout(Duration.ofSeconds(10L)).build();
        ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory(okHttpClient);
        return new RestTemplate(factory);
    }
}
