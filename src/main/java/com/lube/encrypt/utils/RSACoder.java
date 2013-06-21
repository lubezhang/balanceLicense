package com.lube.encrypt.utils;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA安全编码组件
 *
 * @author zhangqh
 * @version 1.0
 * @since 1.0
 */
public class RSACoder {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String PUBLIC_KEY;

    static{
        StringBuilder publicKeyBuf = new StringBuilder();
        publicKeyBuf.append("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCC0hdBJOdtER0QlnPz2D1NfNmSrwulzSrn4sDb");
        publicKeyBuf.append("PWBQiMTl7pmAvVmBVuxPWS1ocfCBOrGNrSpGnzxhQS0dQOwmAW9dnvQ8YYFb+JPsvOGJMi/wpllh");
        publicKeyBuf.append("RD2eavQ1wL2+7V/7OqDwz6HcH21NVRA8Ce7nJsDWiD75fDZwW4N47DaJ5QIDAQAB");
        PUBLIC_KEY = publicKeyBuf.toString();
    }

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    /**
     * 解密<br>
     * 用私钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    private static String decryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return new String(cipher.doFinal(data));
    }

    /**
     * 解密<br>
     * 用公钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    private static byte[] decryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 加密<br>
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    private static byte[] encryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对公钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 使用公钥加密
     * @param str
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String str) throws Exception {
        return encryptBASE64(encryptByPublicKey(str.getBytes(), PUBLIC_KEY));
    }

    /**
     * 公钥解密
     * @param str
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(String str) throws Exception {
        return new String(decryptByPublicKey(decryptBASE64(str), PUBLIC_KEY));
    }

}
