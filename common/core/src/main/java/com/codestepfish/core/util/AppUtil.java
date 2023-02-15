package com.codestepfish.core.util;

import com.codestepfish.core.result.AppException;
import com.codestepfish.core.result.RCode;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

// 常用util
@Slf4j
public class AppUtil {

    // 检查字符串是否符合给定的正则  ,符合 原样返回  , 不符合 抛异常
    public static String checkPattern(String content, String pattern) {
        if (Pattern.matches(pattern, content)) {
            return content;
        }
        log.error("参数: {} 不符合正则表达式要求", content);
        throw new AppException(RCode.PARAM_ERROR);
    }
}
