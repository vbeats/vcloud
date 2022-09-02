package com.codestepfish.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codestepfish.admin.dto.client.ClientQueryIn;
import com.codestepfish.admin.service.AuthClientService;
import com.codestepfish.admin.service.ClientService;
import com.codestepfish.common.result.PageOut;
import com.codestepfish.datasource.entity.AuthClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClientServiceImpl implements ClientService {

    private final AuthClientService authClientService;

    @Override
    public PageOut<List<AuthClient>> list(ClientQueryIn param) {
        LambdaQueryWrapper<AuthClient> query = Wrappers.lambdaQuery();

        query.isNull(AuthClient::getDeleteTime).orderByAsc(AuthClient::getCreateTime);

        if (StringUtils.hasText(param.getClientId())) {
            query.eq(AuthClient::getClientId, param.getClientId());
        }
        Page<AuthClient> pages = authClientService.page(new Page<>(param.getCurrent(), param.getPageSize()), query);

        PageOut<List<AuthClient>> out = new PageOut<>();

        out.setTotal(pages.getTotal());
        out.setRows(pages.getRecords());

        return out;
    }

    @Override
    public void add(AuthClient client) {
        AuthClient exist = authClientService.getOne(Wrappers.<AuthClient>lambdaQuery().eq(AuthClient::getClientId, client.getClientId()));
        Assert.isNull(exist, "客户端ID已存在");

        client.setCreateTime(LocalDateTime.now());
        authClientService.save(client);
    }

    @Override
    public void update(AuthClient client) {
        AuthClient exist = authClientService.getOne(Wrappers.<AuthClient>lambdaQuery().eq(AuthClient::getClientId, client.getClientId()));
        Assert.isTrue(ObjectUtils.isEmpty(exist) || exist.getId().equals(client.getId()), "客户端ID已存在");

        client.setUpdateTime(LocalDateTime.now());
        authClientService.updateById(client);
    }

    @Override
    public void delete(AuthClient client) {
        AuthClient authClient = authClientService.getById(client.getId());
        authClient.setDeleteTime(LocalDateTime.now());

        authClientService.updateById(authClient);
    }
}
