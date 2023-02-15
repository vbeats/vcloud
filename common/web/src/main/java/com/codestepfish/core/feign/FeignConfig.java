package com.codestepfish.core.feign;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.codestepfish.core.result.AppException;
import com.codestepfish.core.result.R;
import com.codestepfish.core.result.RCode;
import feign.RequestInterceptor;
import feign.Response;
import feign.codec.StringDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

@Slf4j
public class FeignConfig {

    @Bean   // 请求拦截器   请求头添加 token
    public RequestInterceptor requestInterceptor() {
        SaTokenConfig saTokenConfig = SaManager.getConfig();
        return requestTemplate -> requestTemplate.header(saTokenConfig.getTokenName(), StpUtil.getTokenValue());
    }

    @Bean  // 响应拦截器  拦截code != 200的异常
    public StringDecoder stringDecoder() {
        return new StringDecoder() {
            @Override
            public Object decode(Response response, Type type) throws IOException {
                String res = IOUtils.toString(response.body().asReader(StandardCharsets.UTF_8));
                R r = JSON.parseObject(res, R.class);
                if (!r.getCode().equals(RCode.SUCCESS.getCode())) {
                    throw new AppException(r.getCode(), r.getMsg());
                }

                if (r.getData() instanceof JSONObject) {
                    return JSON.parseObject(JSON.toJSONString(r.getData()), type, JSONReader.Feature.SupportSmartMatch);
                } else if (r.getData() instanceof JSONArray) {
                    return JSON.parseArray(JSON.toJSONString(r.getData()), type, JSONReader.Feature.SupportSmartMatch);
                }

                return null;
            }
        };
    }
}
