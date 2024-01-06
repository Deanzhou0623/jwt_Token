package com.roadjava.jwt.util;

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
import com.roadjava.jwt.bean.User;

import java.util.Calendar;

/**
 * @author zhaodaowen
 * @see <a href="http://www.roadjava.com">乐之者java</a>
 */
public class JwtUtil {
    private static final String KEY = "123456abc";

    /**
     * 生成token
     * @param user
     * @return
     */
    public static String generateToken(User user) {
        // 过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR,7);
        // claim:声明
        JWTCreator.Builder builder = JWT.create()
                // payload的内容,由一个个的claim组成
                .withClaim("userInfo", JSON.toJSONString(user))
                .withExpiresAt(calendar.getTime());
        String token = builder.sign(Algorithm.HMAC256(KEY));
        return token;
    }

    /**
     * 校验
     * @param tokenToBeVerify
     * @return
     */
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
