package com.codestepfish.admin.controller;

import com.codestepfish.admin.dto.region.RegionVo;
import com.codestepfish.admin.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/region")
public class RegionController {

    private final RegionService regionService;

    @PostMapping("/list")
    public List<RegionVo> list(@RequestBody RegionVo param) {
        return regionService.listRegionLazy(param.getPid());
    }
}
