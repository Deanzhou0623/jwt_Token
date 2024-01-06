package com.roadjava.jwt.servlet;

import com.alibaba.fastjson.JSON;
import com.roadjava.jwt.bean.User;
import com.roadjava.jwt.util.JwtUtil;
import com.roadjava.jwt.util.RespUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试前后端分离使用jwt的servlet
 */
@WebServlet(name = "UserServlet2",urlPatterns = "/user2")
public class UserServlet2 extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        if ("login".equals(type)) {
            String userName = req.getParameter("userName");
            String pwd = req.getParameter("pwd");
            System.out.println("userName2:"+userName+",pwd2:"+pwd);
            // 假装校验通过
            // 生成jwt的token
            User user = new User();
            user.setId(99);
            user.setName(userName);
            user.setAddr("河南");
            String token = JwtUtil.generateToken(user);
            Map<String,Object> map = new HashMap<>();
            map.put("token",token);
            RespUtil.responseAppJson(resp,map);
        }else if ("list".equals(type)) {
            System.out.println("---UserServlet2查询用户列表---");
            Map<String,Object> map = new HashMap<>();
            map.put("status",200);
            RespUtil.responseAppJson(resp,map);
        }
    }

}
