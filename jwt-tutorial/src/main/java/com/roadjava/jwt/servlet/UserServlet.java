package com.roadjava.jwt.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserServlet",urlPatterns = "/user")
public class UserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        if ("login".equals(type)) {
            String userName = req.getParameter("userName");
            String pwd = req.getParameter("pwd");
            System.out.println("userName:"+userName+",pwd:"+pwd);
            // 假装校验通过
            req.getSession().setAttribute("userName",userName);
            // 向浏览器写入cookie(小甜饼)
            Cookie cookie = new Cookie("userName", userName + "123456");
            // 秒
            cookie.setMaxAge(7*24*60*60);
            cookie.setPath("/");
            resp.addCookie(cookie);

            req.getRequestDispatcher("/index.jsp").forward(req,resp);
        }else if ("list".equals(type)) {
            System.out.println("---查询用户列表---");
            // 就是浏览器发送来的JSESSIONID的值
            System.out.println("sessionId:"+req.getSession().getId());
            // 获取到客户端出来的cookie
            Cookie[] cookies = req.getCookies();
            for (Cookie c:cookies) {
                System.out.println("cookieName:"+c.getName()+",cookieValue:"+c.getValue() );
            }
            req.getRequestDispatcher("/index.jsp").forward(req,resp);
        }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}
