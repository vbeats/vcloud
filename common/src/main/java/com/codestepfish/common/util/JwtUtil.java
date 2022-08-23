package com.codestepfish.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Verification;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class JwtUtil {
    private static final String iss = "vcloud";
    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // 签名密钥  长度 64
    private static final String key = Base64Utils.encodeToString("tSG~1&OoP%PVo%8BLp(S8DA0QG!r#Q(0I#FeWlRVTwhkgm3m+DpVg6xlTyf!)VIm".getBytes());

    /**
     * 生成 JWT Token 字符串
     *
     * @param ttl    jwt 过期时间
     * @param claims 额外添加到payload部分的信息。
     *               例如可以添加用户名、用户ID、用户（加密前的）密码等信息
     */
    public static String encode(LocalDateTime ttl, Map<String, Object> claims) {
        if (claims == null) {
            claims = new HashMap<>();
        }

        // 签发时间（iat）：payload部分的标准字段之一
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder()
                // payload部分的非标准字段/附加字段，一般写在标准的字段之前。
                .setClaims(claims)
                // JWT ID（jti）：payload部分的标准字段之一，JWT 的唯一性标识，虽不强求，但尽量确保其唯一性。
                .setId(UUID.randomUUID().toString())
                // 签发时间（iat）：payload部分的标准字段之一，代表这个 JWT 的生成时间。
                .setIssuedAt(now)
                // 签发人（iss）：payload部分的标准字段之一，代表这个 JWT 的所有者。通常是 username、userid 这样具有用户代表性的内容。
                .setSubject(iss)
                // 设置生成签名的算法和秘钥
                .signWith(signatureAlgorithm, key)
                // 过期时间
                .setExpiration(Date.from(ObjectUtils.isEmpty(ttl) ? LocalDateTime.now().atOffset(ZoneOffset.of("+8")).toInstant()
                        : ttl.atOffset(ZoneOffset.of("+8")).toInstant()));

        return builder.compact();
    }

    /**
     * JWT Token 由 头部 payload 和 签名部 三部分组成。签名部分是由加密算法生成，无法反向解密。
     * 而 头部 和 payload是由 Base64 编码算法生成，是可以反向反编码回原样的。
     * 这也是为什么不要在 JWT Token 中放敏感数据的原因。
     *
     * @param jwtToken 加密后的token
     * @return claims 返回payload的键值对
     */
    public static Claims decode(String jwtToken) {
        try {

            // 得到 DefaultJwtParser
            return Jwts.parser()
                    .setSigningKey(key)
                    // 设置需要解析的 jwt
                    .parseClaimsJws(jwtToken)
                    .getBody();
        } catch (Exception e) {
            log.error("jwt解析失败");
        }
        return null;
    }

    /**
     * 校验 token
     * 在这里可以使用官方的校验，或，
     * 自定义校验规则，例如在 token 中携带密码，进行加密处理后和数据库中的加密密码比较。
     *
     * @param jwtToken 被校验的 jwt Token
     */
    public static boolean isVerify(String jwtToken) {
        boolean flag = false;
        JWTVerifier verifier = verification().build();

        try {
            verifier.verify(jwtToken);
            flag = true;
        } catch (JWTVerificationException e) {
            log.error("jwt过期或其它异常: {}", jwtToken);
        }

        return flag;
    }

    private static Verification verification() {
        Algorithm algorithm = null;

        switch (signatureAlgorithm) {
            case HS256:
                algorithm = Algorithm.HMAC256(Base64Utils.decodeFromString(key));
                break;
            default:
                throw new RuntimeException("不支持该算法");
        }

        return JWT.require(algorithm);
    }

}
