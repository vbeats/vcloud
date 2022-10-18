package com.codestepfish.datasource.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codestepfish.datasource.entity.Menu;
import com.codestepfish.datasource.mapper.MenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MenuService extends ServiceImpl<MenuMapper, Menu> implements IService<Menu> {

    private final MenuMapper menuMapper;

    public List<Menu> listMenu() {
        return menuMapper.listMenu();
    }

    public List<Menu> subMenu(Long pid) {
        return menuMapper.subMenu(pid);
    }
}
