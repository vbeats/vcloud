package com.codestepfish.admin.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codestepfish.admin.dto.menu.MenuOut;
import com.codestepfish.admin.mapper.MenuMapper;
import com.codestepfish.datasource.entity.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MenuService extends ServiceImpl<MenuMapper, Menu> implements IService<Menu> {

    private final MenuMapper menuMapper;

    public List<MenuOut> listMenu() {
        return menuMapper.listMenu();
    }

    public List<MenuOut> subMenu(Long pid) {
        return menuMapper.subMenu(pid);
    }

    public void add(Menu menu) {
        Menu exist = this.getOne(Wrappers.<Menu>lambdaQuery().eq(Menu::getKey, menu.getKey()).isNull(Menu::getDeleteTime));
        Assert.isNull(exist, "key已存在");

        this.save(menu);
    }
}
