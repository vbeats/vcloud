package com.codestepfish.admin.controller;

import com.codestepfish.admin.dto.client.ClientQueryIn;
import com.codestepfish.admin.service.ClientService;
import com.codestepfish.common.result.PageOut;
import com.codestepfish.core.annotation.PreAuth;
import com.codestepfish.datasource.entity.AuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@PreAuth
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/list")
    public PageOut<List<AuthClient>> list(@RequestBody ClientQueryIn param) {
        return clientService.list(param);
    }

    @PostMapping("/add")
    public void add(@RequestBody AuthClient client) {
        clientService.add(client);
    }

    @PostMapping("/update")
    public void update(@RequestBody AuthClient client) {
        clientService.update(client);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody AuthClient client) {
        clientService.delete(client);
    }
}
