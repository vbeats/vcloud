package com.codestepfish.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codestepfish.admin.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> listMenu();

    List<Menu> subMenu(@Param("pid") Long pid);
}
