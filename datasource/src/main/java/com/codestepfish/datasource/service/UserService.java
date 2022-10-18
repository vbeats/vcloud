package com.codestepfish.datasource.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codestepfish.datasource.entity.User;
import com.codestepfish.datasource.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService extends ServiceImpl<UserMapper, User> implements IService<User> {

    /**
     * @param tenantId     租户id
     * @param openConfigId 第三方开发平台id
     * @param openid       微信用户openid
     * @return
     */
    public User findByTenantIdAndWxOpenId(Long tenantId, Long openConfigId, String openid) {
        return this.getOne(Wrappers.<User>lambdaQuery().eq(User::getTenantId, tenantId)
                .apply("open_info->'$.openConfigId'={0}", openConfigId)
                .apply("open_info->'$.wxMiniApp.openid'={0}", openid)
        );
    }
}
