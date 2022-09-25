package com.codestepfish.datasource.entity;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.*;
import me.ahoo.cosid.annotation.CosId;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 管理员
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`user`")
public class User implements Serializable {
    private static final long serialVersionUID = 6212633406797709469L;

    @CosId(value = "user")
    private Long id;

    /**
     * 所属租户id
     */
    @TableField(value = "tenant_id")
    private Long tenantId;

    /**
     * 第三方开放平台id
     */
    @TableField(value = "open_id")
    private Long openId;

    /**
     * 第三方开放平台用户信息(unionid openid...)
     */
    @TableField(value = "open_info", typeHandler = FastjsonTypeHandler.class)
    private JSONObject openInfo;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;

    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    @TableField(value = "delete_time")
    private LocalDateTime deleteTime;
}