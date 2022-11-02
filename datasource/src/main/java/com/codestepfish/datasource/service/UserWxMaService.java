package com.codestepfish.datasource.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codestepfish.datasource.entity.User;
import com.codestepfish.datasource.entity.UserWxMa;
import com.codestepfish.datasource.mapper.UserWxMaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserWxMaService extends ServiceImpl<UserWxMaMapper, UserWxMa> implements IService<UserWxMa> {

    private final UserWxMaMapper userWxMaMapper;

    public User findByTenantIdAndOpenId(Long tenantId, Long openConfigId, String openid) {
        return userWxMaMapper.findByTenantIdAndOpenId(tenantId, openConfigId, openid);
    }
}
