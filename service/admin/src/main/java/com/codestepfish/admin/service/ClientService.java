package com.codestepfish.admin.service;

import com.codestepfish.admin.dto.client.ClientQueryIn;
import com.codestepfish.common.result.PageOut;
import com.codestepfish.datasource.entity.AuthClient;

import java.util.List;

public interface ClientService {
    PageOut<List<AuthClient>> list(ClientQueryIn param);

    void add(AuthClient client);

    void update(AuthClient client);

    void delete(AuthClient client);
}
