package com.sydata.framework.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.symmetric.AES;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * @author lzq
 * @description AES加解密工具类
 * @date 2022/10/21 19:11
 */
public final class AesUtil {

    private static final KeyGenerator KEY_GENERATOR;

    static {
        try {
            KEY_GENERATOR = KeyGenerator.getInstance("AES");
            KEY_GENERATOR.init(128);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解密
     *
     * @param secret           秘钥
     * @param ciphertextBase64 加密后再Base64编码的内容
     * @return 明文
     */
    public static String decryptBase64(String secret, String ciphertextBase64) {
        byte[] decode = Base64.decode(ciphertextBase64);
        return new AES(secret.getBytes()).decryptStr(decode);
    }

    /**
     * 解密
     *
     * @param secret 秘钥
     * @param bytes  加密后再Base64编码的字节数组
     * @return 明文
     */
    public static byte[] decryptBase64Bytes(String secret, byte[] bytes) {
        byte[] decode = Base64.decode(bytes);
        return new AES(secret.getBytes()).decrypt(decode);
    }

    /**
     * 加密
     *
     * @param secret  秘钥
     * @param content 内容
     * @return 明文
     */
    public static String encryptBase64(String secret, String content) {
        return new AES(secret.getBytes()).encryptBase64(content);
    }


    /**
     * 生成AES秘钥
     *
     * @return AES秘钥
     */
    public static String generateSecret() {
        byte[] bytes = KEY_GENERATOR.generateKey().getEncoded();
        return HexUtil.encodeHexStr(bytes);
    }
}
