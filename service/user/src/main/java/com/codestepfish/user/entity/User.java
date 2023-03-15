package com.codestepfish.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codestepfish.core.serializer.LocalDateTimeDeserializer;
import com.codestepfish.core.serializer.LocalDateTimeSerializer;
import com.codestepfish.core.serializer.LongToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.ahoo.cosid.annotation.CosId;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@TableName(value = "`user`")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1149977491607490520L;

    @CosId(value = "user")
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;

    /**
     * 商户id
     */
    @TableField(value = "merchant_id")
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long merchantId;

    /**
     * 微信开放平台union id
     */
    @TableField(value = "wx_union_id")
    private String wxUnionId;

    /**
     * 微信小程序openid
     */
    @TableField(value = "wx_ma_openid")
    private String wxMaOpenid;

    /**
     * 微信公众平台openid
     */
    @TableField(value = "wx_mp_openid")
    private String wxMpOpenid;

    /**
     * 手机号 -- 默认 微信绑定的手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 状态 0禁用  1正常
     */
    @TableField(value = "`status`")
    private Boolean status;

    /**
     * 是否删除 0否 1是
     */
    @TableField(value = "del_flag")
    private Boolean delFlag;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;
}
