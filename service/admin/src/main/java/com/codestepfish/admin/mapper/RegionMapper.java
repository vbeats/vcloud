package com.codestepfish.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codestepfish.admin.dto.region.RegionVo;
import com.codestepfish.datasource.entity.Region;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RegionMapper extends BaseMapper<Region> {
    List<RegionVo> listRegionLazy(@Param("pid") Long pid);
}
