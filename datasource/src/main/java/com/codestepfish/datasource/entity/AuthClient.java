package com.codestepfish.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codestepfish.common.serializer.LocalDateTimeDeserializer;
import com.codestepfish.common.serializer.LocalDateTimeSerializer;
import com.codestepfish.common.serializer.LongToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.time.LocalDateTime;

/**
 * client参数
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "auth_client")
public class AuthClient {

    @JsonSerialize(using = LongToStringSerializer.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * client id
     */
    @TableField(value = "client_id")
    private String clientId;

    /**
     * secret
     */
    @TableField(value = "client_secret")
    private String clientSecret;

    /**
     * 授权类型
     */
    @TableField(value = "grant_types")
    private String grantTypes;

    /**
     * access_token有效时长 (秒) 默认7200
     */
    @TableField(value = "access_token_expire")
    private Long accessTokenExpire;

    /**
     * refresh_token有效时长 (天) 默认30
     */
    @TableField(value = "refresh_token_expire")
    private Long refreshTokenExpire;

    /**
     * rsa私钥
     */
    @TableField(value = "private_key")
    private String privateKey;

    /**
     * rsa公钥
     */
    @TableField(value = "public_key")
    private String publicKey;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @TableField(value = "delete_time")
    private LocalDateTime deleteTime;
}