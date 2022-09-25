package com.codestepfish.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codestepfish.admin.dto.open.OpenConfigVo;
import com.codestepfish.admin.mapper.OpenConfigMapper;
import com.codestepfish.datasource.entity.OpenConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OpenConfigService extends ServiceImpl<OpenConfigMapper, OpenConfig> implements IService<OpenConfig> {

    private final OpenConfigMapper openConfigMapper;

    public Page<OpenConfigVo> listConfigs(Page<OpenConfigVo> page, Long tenantId, String name) {
        return openConfigMapper.listConfigs(page, tenantId, name);
    }
}
