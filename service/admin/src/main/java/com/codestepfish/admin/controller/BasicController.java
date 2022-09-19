package com.codestepfish.admin.controller;

import cn.hutool.core.lang.tree.Tree;
import com.codestepfish.admin.dto.admin.AdminInfo;
import com.codestepfish.admin.dto.admin.PasswordIn;
import com.codestepfish.admin.dto.menu.MenuQueryParam;
import com.codestepfish.admin.service.BasicService;
import com.codestepfish.common.model.AppUser;
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
    public List<Tree<String>> menus(@RequestBody MenuQueryParam param, AppUser user) {
        return basicService.menus(param, user);
    }

    @PostMapping("/profile")
    public AdminInfo profile(AppUser user) {
        return basicService.profile(user);
    }

    @PostMapping("/updateProfile")
    public void updateProfile(@RequestBody PasswordIn param, AppUser user) {
        basicService.updateProfile(param, user);
    }
}
