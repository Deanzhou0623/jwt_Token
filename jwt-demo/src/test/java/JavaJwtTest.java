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
*@ClassName:  JavaJwtTest
*@Description test java jwt
*@Auther DeanZhou
*@Date 2024-01-05 15:20
**/
public class JavaJwtTest {

    /*
    * generate java-jwt token string
    * token : header(default) + playLoad(claims)+signature
    * */

    /*
    * key/salt
    * */
    String KEY="deanZhou";
    @Test
    public void testGenerateToken(){
        //1.set time
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.YEAR,100);
        //2.generate token
        JWTCreator.Builder builder = JWT.create()
                //2.1claims
                .withClaim("userId",123)
                .withClaim("pwd",456).
                //2.2set expire time
                withExpiresAt(instance.getTime());
        //3 add signature(choose algorithm and need to add a salt(key))
        String token = builder.sign(Algorithm.HMAC256(KEY));
        //4. print token
        System.out.println(token);
    }

    /*
    * verify
    * */
    @Test
    public void testToken(){
        String token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwd2QiOjQ1NiwiZXhwIjo0ODYwMTcxNjczLCJ1c2VySWQiOjEyM30.k7p58xCbwV0iyyb46hBUJVwIZKmJTRL-YDpHO8Ez-I0";
        //1. token test
        DecodedJWT verify=null;
        try {
            verify = JWT.require(Algorithm.HMAC256(KEY)).build().verify(token);
        }catch (SignatureVerificationException e){
            e.printStackTrace();
            System.out.println("signature issue!/签名不一致");
        }catch (TokenExpiredException e){
            e.printStackTrace();
            System.out.println("token is expired/token 过期。");
        }catch (AlgorithmMismatchException e){
            e.printStackTrace();
            System.out.println("algorithm mismatch/算法不匹配。");
        }catch (InvalidClaimException e){
            e.printStackTrace();
            System.out.println("playload is not available/playload 不可用");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("test fail!");
        }

        //2. print 
        System.out.println(verify);

        //3. get info from claims
        if (verify!=null){
         //get info from playLoad ,if the type is different, can not get info
            Integer userId = verify.getClaim("userId").asInt();
            //test- can not get
            String userIdTest = verify.getClaim("userId").asString();
            //get pwd
            Integer pwd = verify.getClaim("pwd").asInt();

            //print
            System.out.println("userId: "+userId);
            System.out.println("pwd: "+pwd);
            System.out.println("test-userId: "+userIdTest);
        }
    }
}
