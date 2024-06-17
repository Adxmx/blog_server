package com.adxm.blog.utils;

import com.adxm.blog.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Adxm
 * @Date: 2022/9/28
 * @Description:
 */
// TODO 私钥
public class JWTUtils {
    private static String secret = "secret";
    private static JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWTUtils.secret)).build();

    public static String generateToken(User user) {
        Calendar calender = Calendar.getInstance();
        calender.add(Calendar.HOUR, 12);
        JWTCreator.Builder builder = JWT.create();
        return builder.withClaim("userId", user.getId())
                .withClaim("username", user.getUsername())
                .withExpiresAt(calender.getTime())
                .sign(Algorithm.HMAC256(JWTUtils.secret));
    }

    public static Map<String, Object> verifyToken(String token){
        Map<String, Object> map = new HashMap<>();
        try {
            DecodedJWT decodedJWT = JWTUtils.verifier.verify(token);
            map.put("userId", decodedJWT.getClaim("userId").asInt());
            map.put("username", decodedJWT.getClaim("username").asString());
        } catch (SignatureVerificationException e) {
            System.out.println("Token 无效签名， 错误：" + e);
        } catch (TokenExpiredException e) {
            System.out.println("Token 时间过期， 错误：" + e);
        } catch (AlgorithmMismatchException e) {
            System.out.println("Token 算法错误， 错误：" + e);
        } catch (Exception e) {
            System.out.println("Token 未知异常， 错误：" + e);
        }
        return map;
    }
}
