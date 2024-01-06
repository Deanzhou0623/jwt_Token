package com.roadjava.jwt.util;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author zhaodaowen
 * @see <a href="http://www.roadjava.com">乐之者java</a>
 */
public class RespUtil {
    public static void responseAppJson(HttpServletResponse resp, Object obj) {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        // 把对象序列化为字符串
        String jsonString = JSON.toJSONString(obj);
        try(PrintWriter writer = resp.getWriter()) {
            writer.print(jsonString);
            writer.flush();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
