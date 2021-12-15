package com.dong.common.core.utils.sign;


import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * AES加密128位CBC模式工具类
 */
public class DecryptUtil {
    // 算法名称
    final String KEY_ALGORITHM = "AES";

    // 加解密算法/模式/填充方式
    final String algorithmStr = "AES/CBC/PKCS7Padding";
    //
    private Key key;
    private Cipher cipher;

    public void init(byte[] keyBytes) {
        // 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
        int base = 16;
        if (keyBytes.length % base != 0) {
            int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
            keyBytes = temp;
        }
        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        // 转化成JAVA的密钥格式
        key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        try {
            // 初始化cipher
            cipher = Cipher.getInstance(algorithmStr);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 解密方法
     *
     *            要解密的字符串
     *            解密密钥
     * @return
     */
    public byte[] decrypt(String encryptedDataStr, String keyBytesStr, String ivStr) {
        byte[] encryptedText = null;
        byte[] encryptedData = null;
        byte[] sessionkey = null;
        byte[] iv = null;

        try {
            sessionkey = Base64.decodeBase64(keyBytesStr);
            encryptedData = Base64.decodeBase64(encryptedDataStr);
            iv = Base64.decodeBase64(ivStr);

            init(sessionkey);

            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
            encryptedText = cipher.doFinal(encryptedData);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return encryptedText;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        DecryptUtil d = new DecryptUtil();
        String content = "k22wtYKRuWZsaDXC5O2re8Z4Nwvpq92z2x17pTUjcdFu5emh4U4cyV9BFuNGY5ch0RJaejRzw4U+Hu/oVytbIlT5JMQzY4PbOCV9+cj03IpWbspivWEWLTvCKJayOMi2FDO2XCmMprAWmtM4uTwK9ePyJ7IEuOP8PZjURQJrtoVEamiN/1dOYd829QxRfxKeAnSk6S4ZRlrzEW7h0/F6fIZi0bOafh6Y1w4Ru9TAx2zE47VzsYvX3WdCB4UllircWQ0ndoXqlfXVQs/FCkH6P/FAoSFy9/fkT27UFvWL+3+XMtVNeo+McmCuvTlKdEf/OP+Wy1KYiE1DDEmeAAvJoPUn8wdiz8KTvbopw96hRsAA6xGTDAH6jPAwtfeUgcDGHU38lrT8PmTPinXBO9krBPZ6zl4pSSKmpPKdxI7ckbY=";
        String key = "0cUV+M9AfPY6Q5zgz197Lw==";
        String iv = "dNTPNN6M6qwhbFyCSFDx9g==";
        byte[] result = d.decrypt(content, key, iv);
        System.out.println(new String(result,"UTF-8"));
    }
}
