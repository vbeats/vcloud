package com.codestepfish.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codestepfish.admin.dto.region.RegionVo;
import com.codestepfish.admin.mapper.RegionMapper;
import com.codestepfish.datasource.entity.Region;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegionService extends ServiceImpl<RegionMapper, Region> implements IService<Region> {

    private final RegionMapper regionMapper;

    public List<RegionVo> listRegionLazy(Long pid) {
        return regionMapper.listRegionLazy(pid);
    }
}
