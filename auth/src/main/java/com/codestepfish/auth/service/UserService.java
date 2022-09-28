package com.codestepfish.auth.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codestepfish.auth.mapper.UserMapper;
import com.codestepfish.datasource.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService extends ServiceImpl<UserMapper, User> implements IService<User> {

    /**
     * @param tenantId 租户id
     * @param open_id  第三方开发平台id
     * @param openid   微信用户openid
     * @return
     */
    public User findByTenantIdAndWxOpenId(Long tenantId, Long open_id, String openid) {
        return this.getOne(Wrappers.<User>lambdaQuery().eq(User::getTenantId, tenantId)
                .apply("open_info->'$.openId'={0}", open_id)
                .apply("open_info->'$.wxMiniApp.openid'={0}", openid)
        );
    }
}
