package com.codestepfish.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codestepfish.admin.mapper.TopMenuMapper;
import com.codestepfish.datasource.entity.TopMenu;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TopMenuService extends ServiceImpl<TopMenuMapper, TopMenu> implements IService<TopMenu> {

    private final TopMenuMapper topMenuMapper;
}
