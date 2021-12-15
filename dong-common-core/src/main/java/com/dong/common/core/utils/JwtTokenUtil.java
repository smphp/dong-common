package com.dong.common.core.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.InputStream;
import java.util.Date;

public class JwtTokenUtil {

    private static InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("smartom.jks"); // 寻找证书文件
    private static String privateKey = "123456";
    private static String publicKey = "123456";

    /**
     *
     * 使用私钥加密 token
     *
     * @param:
     * @return:
     */
    public static String generateToken(String subject, int expirationSeconds) {
        return Jwts.builder()
                .setClaims(null)
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + expirationSeconds * 1000))
                .signWith(SignatureAlgorithm.HS256, privateKey)
                .compact();
    }

    /**
     *
     * 不使用公钥私钥 加密token
     *
     * @param:
     * @return:
     */
    public static String generateToken(String subject, int expirationSeconds, String salt) {
        return Jwts.builder()
                .setClaims(null)
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + expirationSeconds * 1000))
                .signWith(SignatureAlgorithm.HS256, salt) // 不使用公钥私钥
                .compact();
}

    /**
     *
     * 通过 公钥解密token
     *
     * @param:
     * @return:
     */
    public static String parseToken(String token) {
        String subject = null;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(publicKey)
                    .parseClaimsJws(token).getBody();
            subject = claims.getSubject();
        } catch (Exception e) {
        }
        return subject;
    }

    /**
     *
     * 不通过 公钥解密token
     *
     * @param:
     * @return:
     */
    public static String parseToken(String token,String salt) {
        String subject = null;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(salt) // 不使用公钥私钥
                    .parseClaimsJws(token).getBody();
            subject = claims.getSubject();
        } catch (Exception e) {
        }
        return subject;
    }


}