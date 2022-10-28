package com.codestepfish.core.util;

import cn.hutool.extra.spring.SpringUtil;
import com.codestepfish.datasource.entity.Tenant;
import com.codestepfish.datasource.service.TenantService;

/**
 * 租户 关系判断
 */
public class TenantUtil {

    /**
     * p 是否是 sub的上级
     *
     * @param pid
     * @param subId
     * @return
     */
    public static boolean isParent(Long pid, Long subId) {
        TenantService tenantService = SpringUtil.getBean(TenantService.class);
        if (pid.equals(1L) || pid.equals(subId)) {
            return true;
        }

        Tenant sub = tenantService.getById(subId);
        if (sub.getPid().equals(pid)) {
            return true;
        } else if (sub.getPid().equals(0L)) {   // 到头了
            return false;
        } else {
            return isParent(pid, sub.getPid());  // 递归
        }
    }
}
