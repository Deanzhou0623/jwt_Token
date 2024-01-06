import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.util.Calendar;

/**
 * @author zhaodaowen
 * @see <a href="http://www.roadjava.com">乐之者java</a>
 */
public class JavaJwtTest {
    // hmac256摘要算法，跟md5类似,只是这个hmac256需要你指定一个key而已(salt盐)
    String key = "123456abc";
    /**
     * 测试java-jwt生成token字符串
     */
    @Test
    public void testGenerateToken() {
        // 过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,60*60);
        // claim:声明
        JWTCreator.Builder builder = JWT.create()
                // payload的内容,由一个个的claim组成
                .withClaim("userId", 123)
                .withClaim("userName", "乐之者java")
                .withClaim("url", "http://www.roadjava.com")
                .withExpiresAt(calendar.getTime());
        String token = builder.sign(Algorithm.HMAC256(key));
        System.out.println(token);
    }
    @Test
    public void testVerify(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6IuS5kOS5i-iAhWphdmEiLCJleHAiOjE2MjU1MTcxMDIsInVzZXJJZCI6MTIzLCJ1cmwiOiJodHRwOi8vd3d3LnJvYWRqYXZhLmNvbSJ9.OrXBEiqFCs7mUEzx2DQfuF1_kzGDZBnpDpWg5cWo7NQ";
        DecodedJWT verify = null;
        try {
            verify = JWT.require(Algorithm.HMAC256(key)).build().verify(token);
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
        if (verify != null) {
            // 获取payload里面的信息,注意类型，如果类型不一致，是获取不到的
            int userId = verify.getClaim("userId").asInt();
            String userName = verify.getClaim("userName").asString();
            String url = verify.getClaim("url").asString();
            System.out.println("userId:"+userId);
            System.out.println("userName:"+userName);
            System.out.println("url:"+url);
        }
    }
}
