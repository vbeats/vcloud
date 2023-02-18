package com.codestepfish.core.util;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 拼接新的pids字符串
 * <p>
 * pids转 Long 集合
 */
public class PidsUtil {

    /**
     * pids ,分隔 转Set<Long> 去除 0
     *
     * @param pids
     * @return
     */
    public static Set<Long> parsePids(String pids) {
        return Splitter.on(",").trimResults().omitEmptyStrings().splitToList(pids).stream().filter(s -> !"0".equals(s)).mapToLong(Long::valueOf).boxed().collect(Collectors.toSet());
    }

    /**
     * 扩充pids  按升序, 填充新的pid
     *
     * @param pids
     * @param pid
     * @return
     */
    public static String appendPids(String pids, Long pid) {
        Set<Long> pidSet = parsePids(pids);
        pidSet.add(pid);
        return Joiner.on(",").skipNulls().join(pidSet.stream().sorted().collect(Collectors.toList()));
    }
}
