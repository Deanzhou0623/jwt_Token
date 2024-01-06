import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * @author zhaodaowen
 * @see <a href="http://www.roadjava.com">乐之者java</a>
 */
public class JjwtTest {
    String key="123456abc";
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
        claims.put("userName", "乐之者java");
        claims.put("url","http://www.roadjava.com");
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
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6IuS5kOS5i-iAhWphdmEiLCJleHAiOjE2MjU1MTczMDAsInVzZXJJZCI6MTIzLCJ1cmwiOiJodHRwOi8vd3d3LnJvYWRqYXZhLmNvbSJ9.F4Msa0gP1A5WnxGWHa5bcKyCyPJy00KBLvy1Ggqe494";
        Claims claims = Jwts.parser()  //得到DefaultJwtParser
                .setSigningKey(key)         //设置签名用到的key
                .parseClaimsJws(token).getBody();
        Integer userId = claims.get("userId", Integer.class);
        String userName = claims.get("userName", String.class);
        String url = claims.get("url", String.class);
        System.out.println("userId:"+userId);
        System.out.println("userName:"+userName);
        System.out.println("url:"+url);
    }
}
