package com.sydata.framework.core.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import org.springframework.util.DigestUtils;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author lzq
 * @describe 非对称加密工具类
 * @date 2022-04-07 09:58
 */
public final class RsaUtil {


    /**
     * 加密（RSA加密后Base64编码为字符）
     *
     * @param rsa     rsa
     * @param data    明文
     * @param keyType 秘钥类型
     * @return 加密文本
     */
    public static String encryptToBase64(RSA rsa, String data, KeyType keyType) {
        return rsa.encryptBase64(data, UTF_8, keyType);
    }

    /**
     * 解密（将密文字符Base64解码后RSA解密）
     *
     * @param rsa        rsa
     * @param ciphertext 密文
     * @param keyType    秘钥类型
     * @return 解密字节数组
     */
    public static byte[] decryptByBase64(RSA rsa, String ciphertext, KeyType keyType) {
        return rsa.decrypt(Base64.decode(ciphertext), keyType);
    }

    /**
     * 解密（将密文字符Base64解码后RSA解密）
     *
     * @param rsa        rsa
     * @param ciphertext 密文
     * @param keyType    秘钥类型
     * @return 解密文本
     */
    public static String decryptByBase64Str(RSA rsa, String ciphertext, KeyType keyType) {
        return new String(decryptByBase64(rsa, ciphertext, keyType), UTF_8);
    }

    /**
     * MD5参数签名
     *
     * @param data 数据
     * @return MD5摘要
     */
    public static String md5Sign(String data) {
        return DigestUtils.md5DigestAsHex(data.getBytes(UTF_8));
    }
}
