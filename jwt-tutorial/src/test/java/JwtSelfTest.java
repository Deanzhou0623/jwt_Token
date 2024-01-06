import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.roadjava.jwt.bean.User;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

/**
 * 手写jwt的实现
 * @author zhaodaowen
 * @see <a href="http://www.roadjava.com">乐之者java</a>
 */
public class JwtSelfTest {
    String utf8 = "utf-8";
    String key = "123456abc";

    /**
     * 自己模拟生成jwt的token
     * @throws Exception
     */
    @Test
    public void test1() throws Exception{
        // 获取header
        JSONObject originalHeader = new JSONObject();
        originalHeader.put("typ","JWT");
        originalHeader.put("alg","HS256");
        String header = Base64.encodeBase64URLSafeString(originalHeader.toJSONString().getBytes(utf8));
        // 获取payload载荷
        JSONObject originalPayload = new JSONObject();
        User user = new User();
        user.setId(99);
        user.setName("aaa");
        user.setAddr("河南");
        originalPayload.put("userInfo",JSON.toJSONString(user));
        String payload = Base64.encodeBase64URLSafeString(originalPayload.toJSONString().getBytes(utf8));
        // 获取signature
        String signature = hmacSha256Encode(header, payload);
        String token = String.format("%s.%s.%s", header, payload, signature);
        System.out.println(token);
    }

    public String hmacSha256Encode(String header,String payload) throws Exception{
        // 获取提供摘要算法的对象
        Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
        // 指定算法的key
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(utf8), "HmacSHA256");
        hmacSHA256.init(secretKeySpec);
        // 进行摘要
        hmacSHA256.update(header.getBytes(utf8));
        hmacSHA256.update(".".getBytes(utf8));
        byte[] bytes = hmacSHA256.doFinal(payload.getBytes(utf8));
        return Base64.encodeBase64URLSafeString(bytes);
    }
}
