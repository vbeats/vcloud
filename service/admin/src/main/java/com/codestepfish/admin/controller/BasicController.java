package com.codestepfish.admin.controller;

import cn.hutool.core.lang.tree.Tree;
import com.codestepfish.admin.service.BasicService;
import com.codestepfish.common.model.AppUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BasicController {

    private final BasicService basicService;

    @GetMapping("/menus")
    public List<Tree<Long>> menus(AppUser user) {
        return basicService.menus(user);
    }
}
