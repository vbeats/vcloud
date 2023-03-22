package com.codestepfish.core.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA非对称加密解密工具类
 */
@Slf4j
public class RsaUtil {
    private static Cipher cipher;

    static {
        try {
            cipher = Cipher.getInstance("RSA");
        } catch (Exception e) {
            log.error("RSA cipher初始化异常", e);
        }
    }

    /**
     * 生成密钥对
     *
     * @return map
     */
    public static Map<String, String> generateKeyPair() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            // 密钥位数
            keyPairGen.initialize(4096);
            // 密钥对
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 公钥
            PublicKey publicKey = keyPair.getPublic();
            // 私钥
            PrivateKey privateKey = keyPair.getPrivate();
            //得到公钥字符串
            String publicKeyString = getKeyString(publicKey);
            //得到私钥字符串
            String privateKeyString = getKeyString(privateKey);
            //将生成的密钥对返回
            Map<String, String> map = new HashMap<>();
            map.put("publicKey", publicKeyString);
            map.put("privateKey", privateKeyString);
            return map;
        } catch (Exception e) {
            log.error("RSA密钥对生成失败");
        }
        return null;
    }

    /**
     * 清理密钥中无用字符
     *
     * @param key 原始密钥
     * @return 清理后的有效字符
     */
    public static String cleanKey(String key) {
        return key.replaceAll("\n", "").replaceAll("\r", "")
                .replaceAll("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll("-----END PUBLIC KEY-----", "")
                .replaceAll("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll("-----END PRIVATE KEY-----", "").replaceAll("\\s+", "")
                ;
    }

    /**
     * 获取公钥有效的字符串
     *
     * @param key 密钥字符串（base64编码）
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.getDecoder().decode(cleanKey(key));
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 获取私钥有效的字符串
     *
     * @param key 密钥字符串（base64编码）
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.getDecoder().decode(cleanKey(key));
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 密钥字符串（base64编码）
     *
     * @return string
     */
    public static String getKeyString(Key key) {
        byte[] keyBytes = key.getEncoded();
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    /**
     * 加密
     *
     * @param publicKey 公钥
     * @param plainText 明文
     * @return base64密文
     */
    public static String encrypt(PublicKey publicKey, String plainText) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] enBytes = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(enBytes);
        } catch (Exception e) {
            log.error("RSA加密数据异常", e);
        }
        return null;
    }


    /**
     * 加密
     *
     * @param publicKey 公钥
     * @param plainText 明文
     * @return base64密文
     */
    public static String encrypt(String publicKey, String plainText) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
            byte[] enBytes = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(enBytes);
        } catch (Exception e) {
            log.error("RSA加密数据异常", e);
        }
        return null;
    }

    /**
     * 解密
     *
     * @param privateKey    私钥
     * @param encryptedData 密文
     * @return 明文
     */
    public static String decrypt(PrivateKey privateKey, String encryptedData) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] deBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(deBytes);
        } catch (Exception e) {
            log.error("RSA解密数据异常", e);
        }
        return null;
    }

    /**
     * 解密
     *
     * @param privateKey    私钥
     * @param encryptedData 密文
     * @return 明文
     */
    public static String decrypt(String privateKey, String encryptedData) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));
            byte[] deBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(deBytes);
        } catch (Exception e) {
            log.error("RSA解密数据异常", e);
        }
        return null;
    }
}
