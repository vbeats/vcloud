package com.codestepfish.admin.controller;

import cn.hutool.core.lang.tree.Tree;
import com.codestepfish.admin.dto.admin.AdminInfo;
import com.codestepfish.admin.dto.admin.PasswordIn;
import com.codestepfish.admin.dto.menu.MenuQueryParam;
import com.codestepfish.admin.service.BasicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BasicController {

    private final BasicService basicService;

    @PostMapping("/menus")
    public List<Tree<String>> menus(@RequestBody MenuQueryParam param) {
        return basicService.menus(param);
    }

    @PostMapping("/profile")
    public AdminInfo profile() {
        return basicService.profile();
    }

    @PostMapping("/updateProfile")
    public void updateProfile(@RequestBody PasswordIn param) {
        basicService.updateProfile(param);
    }
}
