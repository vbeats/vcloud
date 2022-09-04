package com.codestepfish.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codestepfish.admin.mapper.TopMenuTreeMapper;
import com.codestepfish.datasource.entity.TopMenuTree;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TopMenuTreeService extends ServiceImpl<TopMenuTreeMapper, TopMenuTree> implements IService<TopMenuTree> {

    private final TopMenuTreeMapper topMenuTreeMapper;
}
