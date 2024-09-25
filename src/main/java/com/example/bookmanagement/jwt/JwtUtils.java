package com.example.bookmanagement.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.bookmanagement.model.Account;

import java.util.Calendar;
import java.util.Date;

public class JwtUtils {

    /**
     * 签发对象：这个用户的id 签发时间：现在 有效时间：30分钟 载荷内容：暂时设计为：这个人的名字，这个人的email 加密密钥：这个人的id加上一串字符串
     */
    public static String createToken(Account account) {

        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, 30);
        Date expiresDate = nowTime.getTime();

        return JWT.create().withAudience(String.valueOf(account.getId())) // 签发对象
                .withIssuedAt(new Date()) // 发行时间
                .withExpiresAt(expiresDate) // 有效时间
                .withClaim("userName", account.getName()) // 载荷，随便写几个都可以
                .withClaim("email", account.getEmail()).sign(Algorithm.HMAC256("HelloJwt")); // 加密
    }

    /**
     * 检验合法性
     * 
     * @param token
     * @throws TokenUnavailable
     */
    public static boolean verifyToken(String token) throws Exception {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256("HelloJwt")).build();
            jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取签发对象
     */
    public static String getAudience(String token) throws Exception {
        String audience = null;
        try {
            audience = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            // 这里是token解析失败
            throw new Exception();
        }
        return audience;
    }

    /**
     * 通过载荷名字获取载荷的值
     */
    public static Claim getClaimByName(String token, String name) {
        return JWT.decode(token).getClaim(name);
    }
}