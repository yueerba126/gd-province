package com.sydata.framework.core.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.digest.MD5;

import java.time.format.DateTimeFormatter;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author lzq
 * @describe 客户端非对称加密工具类
 * @date 2022-04-07 09:58
 */
public final class ClientRsaUtil {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final String SIGN = "sign";
    public static final String TIMESTAMP = "timestamp";

    private final static String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCn6/3c7GTpe9p9Ez5+pKNazKmJGYaTny" +
            "B+ELe1pEZ7aoKK6UVySzkdax84k43eMsU25g4zf9JxL3hExJPMUH+9zPuv6/oRRfnHSaZzS27rFkOPqp91eEYai/6B8XT1M5e5AV5+j" +
            "927bs1ZeqB7Wsav5oRTbgfWq8iJIvxD1fViaQIDAQAB";

    private final static RSA RSA = new RSA(null, PUBLIC_KEY);

    /**
     * 加密（RSA加密后Base64编码为字符）
     *
     * @param data 明文
     * @return 加密文本
     */
    public static String encrypt(String data) {
        return RSA.encryptBase64(data, UTF_8, KeyType.PublicKey);
    }

    /**
     * 解密（将密文字符Base64解码后RSA解密）
     *
     * @param ciphertext 密文
     * @return 解密字节数组
     */
    public static byte[] decrypt(String ciphertext) {
        return RSA.decrypt(Base64.decode(ciphertext), KeyType.PublicKey);
    }

    /**
     * 解密（将密文字符Base64解码后RSA解密）
     *
     * @param ciphertext 密文
     * @return 解密文本
     */
    public static String decryptStr(String ciphertext) {
        return new String(decrypt(ciphertext), UTF_8);
    }


    /**
     * MD5参数签名（时间加盐）
     *
     * @param data      数据
     * @param timestamp 时间戳
     * @return MD5摘要
     */
    public static String md5Sign(String data, long timestamp) {
        return MD5.create()
                .setSalt(String.valueOf(timestamp).getBytes(UTF_8))
                .digestHex(data, UTF_8);
    }
}
