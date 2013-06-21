package com.lube.encrypt.utils;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.*;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA安全编码组件
 *
 * @author zhangqh
 * @version 1.0
 * @since 1.0
 */
public class RSACoderPrivate {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final String RSA_PUBLIC_KEY = "RSAPublicKey";
    private static final String RSA_PRIVATE_KEY = "RSAPrivateKey";

    public static final String PUBLIC_KEY;
    public static final String PRIVATE_KEY;

    static{
        StringBuilder publicKeyBuf = new StringBuilder();
        publicKeyBuf.append("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCC0hdBJOdtER0QlnPz2D1NfNmSrwulzSrn4sDb");
        publicKeyBuf.append("PWBQiMTl7pmAvVmBVuxPWS1ocfCBOrGNrSpGnzxhQS0dQOwmAW9dnvQ8YYFb+JPsvOGJMi/wpllh");
        publicKeyBuf.append("RD2eavQ1wL2+7V/7OqDwz6HcH21NVRA8Ce7nJsDWiD75fDZwW4N47DaJ5QIDAQAB");
        PUBLIC_KEY = publicKeyBuf.toString();

        StringBuilder privateKeyBuf = new StringBuilder();
        privateKeyBuf.append("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAILSF0Ek520RHRCWc/PYPU182ZKv");
        privateKeyBuf.append("C6XNKufiwNs9YFCIxOXumYC9WYFW7E9ZLWhx8IE6sY2tKkafPGFBLR1A7CYBb12e9DxhgVv4k+y8");
        privateKeyBuf.append("4YkyL/CmWWFEPZ5q9DXAvb7tX/s6oPDPodwfbU1VEDwJ7ucmwNaIPvl8NnBbg3jsNonlAgMBAAEC");
        privateKeyBuf.append("gYBmLp/HXIH/qRxgtoP/dWn8uoZfkqawbR8UXQNyu/AdN5dEWihAbKYa5sBSAj/7kvPenO/Oz/Fz");
        privateKeyBuf.append("lDFfbb9FWE6bCvtdBqjlrD8yeKr9bcezF+V1DpQ5NSFqXcxXNNlsf3RaSDiIUHV4ARhcQfzG8EU4");
        privateKeyBuf.append("hULQ2r7dEPQ3V1r5sNtaAQJBAL1IID3k+K04JPkFRD1UZaaD3DVwA0D5AOUnQSayca6k5BtpVAHu");
        privateKeyBuf.append("dkx0nmlfRyZiw+1pCJcjo4Lrs1WuL1OeL8ECQQCw7rf7o+RPVPk6FNV7sebTZLic6e2sjtejUBYI");
        privateKeyBuf.append("RcQ0Mct5a7hS7YlrEb3NRSaiIkNRXvmsLLrspy9Y5dXiYmMlAkA6fbtY5RTjkmH6geIggaAX4M9/");
        privateKeyBuf.append("w+l9fnUEWykK6EGxEktbHSVUo3fzEGaTcVnvRr3882QwMFcq8eMtKvJ9CHkBAkEAiCBCITMpFvgz");
        privateKeyBuf.append("f8JDtZVcGEwn1WINYHPN6HJXYSn8GIQvHk4IVJU44s2Vj/aiEJ/31wURZqxLbIP8Y5MBCdnUmQJB");
        privateKeyBuf.append("ALQFNfT2ibifG0SQWoTYSYeqr6YhxScdlKDo+MjfrSL3YXRhvcFoRt8V8bbnvNzJpY9NFe8GI4qm");
        privateKeyBuf.append("GztsMg5KiOw=");
        PRIVATE_KEY = privateKeyBuf.toString();
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
     * 加密<br>
     * 用私钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    private static byte[] encryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     */
    public static Map<String, String> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);

        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, String> keyMap = new HashMap<String, String>(2);
        keyMap.put(RSA_PUBLIC_KEY, encryptBASE64(publicKey.getEncoded()));
        keyMap.put(RSA_PRIVATE_KEY, encryptBASE64(privateKey.getEncoded()));
        return keyMap;
    }

    /**
     * 将字节数据写入到文件中
     *
     * @param bytes
     */
    public static void writeBytesToFile(byte[] bytes) {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream("C:/License"));
            out.writeObject(bytes);
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public static byte[] readFileToBytes(String filePath) {
        byte[] bytes = null;
        try {
            FileInputStream input = new FileInputStream(filePath);
            ObjectInputStream objInput = new ObjectInputStream(input);
            bytes = (byte[]) objInput.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return bytes;
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
     * 使用私钥加密
     * @param str
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(String str) throws Exception {
        return encryptBASE64(encryptByPrivateKey(str.getBytes(), PRIVATE_KEY));
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

    /**
     * 私钥解密
     * @param str
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String str) throws Exception {
        return new String(decryptByPrivateKey(decryptBASE64(str), PRIVATE_KEY));
    }

    public static void main(String[] args){
        String seed = "13691576886";
        String pid = "8FC5E436";
        String soPIn = "8FC5E436648C3420";
        String userPin = "a75654c728793814";
//        String str1;
//        String str2 = "MTQzN+Mel6oHoIq/VbJPCFqRw93KuUJ+3pt77EYm8HaCK3tE/OI01doYEsZTuFp1Oy0v20ALtCIVHit7Y4I/DuD1uKYfrXWv+dnnrRjlqKQh55AMEJTxcDCDQ9SsWOeoNjv1hEEiHYfr6CSI30Sekq+ocBMpZKBHVhDZbN6IQoc=";
        try {
//            str1 = RSACoder.encryptByPublicKey(str);
            System.out.println("种子——私钥加密："+ RSACoderPrivate.encryptByPrivateKey(seed));
            System.out.println("PID——私钥加密："+ RSACoderPrivate.encryptByPrivateKey(pid));
            System.out.println("超级用户PID——私钥加密："+ RSACoderPrivate.encryptByPrivateKey(soPIn));
            System.out.println("普通用户PID——私钥加密："+ RSACoderPrivate.encryptByPrivateKey(userPin));
//            System.out.println("公钥加密："+RSACoder.decryptByPublicKey(str1));
//            System.out.println(RSACoder.decryptByPrivateKey(str2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
