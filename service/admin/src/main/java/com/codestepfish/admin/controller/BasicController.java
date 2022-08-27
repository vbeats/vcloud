package com.codestepfish.admin.controller;

import cn.hutool.core.lang.tree.Tree;
import com.codestepfish.admin.dto.menu.MenuQueryParam;
import com.codestepfish.admin.service.BasicService;
import com.codestepfish.common.model.AppUser;
import com.codestepfish.datasource.entity.TopMenu;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BasicController {

    private final BasicService basicService;

    @GetMapping("/menus")
    public List<Tree<String>> menus(@RequestParam MenuQueryParam param, AppUser user) {
        return basicService.menus(param, user);
    }

    @GetMapping("/topMenus")
    public List<TopMenu> topMenus(AppUser user) {
        return basicService.topMenus(user);
    }
}
