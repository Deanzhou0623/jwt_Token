import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
*@ClassName: jjwt test
*@Description test jjwt
*@Auther DeanZhou
*@Date 2024-01-05 15:57
**/
public class JjwtTest {
    String key="deanZhou";
    /**
     * 生成token
     */
    @Test
    public void testGenerateToken(){
        // 过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,60*60);
        //创建payload的私有声明（根据特定的业务需要添加）
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId", 123);
        claims.put("userName", "deanZhou");
        JwtBuilder builder =
                Jwts.builder()
                        .setClaims(claims)
                        .setExpiration(calendar.getTime())
                        //设置签名使用的签名算法和签名使用的秘钥
                        .signWith(SignatureAlgorithm.HS256, key);
        String compact = builder.compact();
        System.out.println(compact);
    }
    /**
     * 校验
     */
    @Test
    public void testVerify() throws Exception{
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6ImRlYW5aaG91IiwiZXhwIjoxNzA0NTAyODcyLCJ1c2VySWQiOjEyM30.Oks01hkfyxH4EtvSfbkE-DKBb3xonrzxDRStktEFm74";
        Claims claims = Jwts.parser()  //得到DefaultJwtParser
                .setSigningKey(key)         //设置签名用到的key
                .parseClaimsJws(token).getBody();
        Integer userId = claims.get("userId", Integer.class);
        String userName = claims.get("userName", String.class);
        System.out.println("userId:"+userId);
        System.out.println("userName:"+userName);
    }
}
