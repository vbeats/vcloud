package com.codestepfish.admin.controller;

import com.codestepfish.admin.service.RoleService;
import com.codestepfish.common.result.PageOut;
import com.codestepfish.datasource.entity.ConfigParam;
import com.codestepfish.datasource.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/list")
    public PageOut<List<Role>> list() {
        return null;
    }

    @PostMapping("/add")
    public void add(@RequestBody ConfigParam configParam) {


    }

    @PostMapping("/update")
    public void update(@RequestBody ConfigParam configParam) {

    }

    @PostMapping("/delete")
    public void delete(@RequestBody ConfigParam configParam) {

    }
}
