package com.codestepfish.core.feign;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONReader;
import com.codestepfish.core.result.AppException;
import com.codestepfish.core.result.R;
import com.codestepfish.core.result.RCode;
import com.codestepfish.core.util.AppContextHolder;
import feign.RequestInterceptor;
import feign.Response;
import feign.codec.Decoder;
import feign.codec.StringDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
public class FeignConfig {

    @Bean   // 请求拦截器   请求头添加 token & same-token
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            Map<String, String> tokenMap = AppContextHolder.get("token");

            if (!CollectionUtils.isEmpty(tokenMap)) {
                tokenMap.forEach(requestTemplate::header);
            }
        };
    }

    @Bean  // 响应拦截器  拦截code != 200的异常
    public Decoder stringDecoder() {
        return new StringDecoder() {
            @Override
            public Object decode(Response response, Type type) throws IOException {
                String res = IOUtils.toString(response.body().asReader(StandardCharsets.UTF_8));
                R r = JSON.parseObject(res, R.class);
                if (!r.getCode().equals(RCode.SUCCESS.getCode())) {
                    throw new AppException(r.getCode(), r.getMsg());
                }

                if (!ObjectUtils.isEmpty(r.getData())) {
                    if (r.getData() instanceof JSONArray) {
                        return JSON.parseArray(JSON.toJSONString(r.getData()), type, JSONReader.Feature.SupportSmartMatch);
                    } else {
                        return JSON.parseObject(JSON.toJSONString(r.getData()), type, JSONReader.Feature.SupportSmartMatch);
                    }
                }
                return null;
            }
        };
    }
}
