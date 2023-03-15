package com.codestepfish.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codestepfish.admin.entity.Merchant;
import com.codestepfish.admin.mapper.MerchantMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MerchantService extends ServiceImpl<MerchantMapper, Merchant> implements IService<Merchant> {

    private final MerchantMapper merchantMapper;

    public Page<Merchant> listMerchant(Page<Merchant> page, Long pid, String code, String merchantName) {
        Merchant param = new Merchant();
        param.setPid(pid);
        param.setCode(code);
        param.setMerchantName(merchantName);
        return merchantMapper.listMerchant(page, param);
    }

    public List<Merchant> subMerchant(Long pid) {
        Merchant param = new Merchant();
        param.setPid(pid);
        return merchantMapper.listMerchant(param);
    }

    public List<Merchant> listMerchantTree(Long merchantId) {
        return merchantMapper.listMerchantTree(merchantId);
    }
}
