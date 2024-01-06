package com.deanZhou.jwt.util;/*
 *ClassName:JwtUtil
 *Description: jwt generate  -- verify -- parse
 *@Author:deanzhou
 *@Date:2024-01-05 16:19
 */

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.deanZhou.bean.User;

import java.util.Calendar;

public class JwtUtil {
    private final static String KEY="deanZhou";
    
    /*
    * generate token
    * */
    public static String generaToken(User user){
        //1.set time
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.YEAR,100);
        //2.generate token
        JWTCreator.Builder builder = JWT.create()
                //2.1claims
                .withClaim("userInfo", JSON.toJSONString(user)).
                //2.2set expire time
                        withExpiresAt(instance.getTime());
        //3 add signature(choose algorithm and need to add a salt(key))
        String token = builder.sign(Algorithm.HMAC256(KEY));
        //4. return token
        return token;
    }

    /*
    * verify token
    * */
    public static DecodedJWT verify(String tokenToBeVerify) {
        DecodedJWT verify = null;
        try {
            verify = JWT.require(Algorithm.HMAC256(KEY)).build().verify(tokenToBeVerify);
        }catch (SignatureVerificationException e) {
            e.printStackTrace();
            System.out.println("签名不一致");
        }catch (TokenExpiredException e) {
            e.printStackTrace();
            System.out.println("令牌过期");
        }catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            System.out.println("签名算法不匹配");
        }catch (InvalidClaimException e) {
            e.printStackTrace();
            System.out.println("payload不可用");
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("校验失败");
        }
        return verify;
    }

    /**
     * 解析为User对象
     * @param decodedJWT
     * @return
     */
    public static User parse(DecodedJWT decodedJWT) {
        Claim claim = decodedJWT.getClaim("userInfo");
        if (claim != null) {
            String userString = claim.asString();
            User user = JSON.parseObject(userString, User.class);
            return user;
        }
        return null;
    }

}
