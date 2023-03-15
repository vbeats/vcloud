package com.codestepfish.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codestepfish.admin.entity.Merchant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MerchantMapper extends BaseMapper<Merchant> {
    Page<Merchant> listMerchant(Page<Merchant> page, @Param("param") Merchant param);

    List<Merchant> listMerchant(@Param("param") Merchant param);

    List<Merchant> listMerchantTree(@Param("merchant_id") Long merchantId);
}
