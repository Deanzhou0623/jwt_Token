/*
 * Copyright (c) 2019.  黄钰朝
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.roadjava.jwt.filter;


import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.roadjava.jwt.bean.User;
import com.roadjava.jwt.util.JwtUtil;
import com.roadjava.jwt.util.RespUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 为了让CorsFilter先执行，改一下AuthFilter--》XAuthFilter
 * @author zhaodaowen
 * @see <a href="http://www.roadjava.com">乐之者java</a>
 */
@WebFilter(
        filterName = "XAuthFilter",
        urlPatterns = {"/user2"}
)
public class XAuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        // 放行OPTIONS类型的请求
        String method = req.getMethod();
        if ("OPTIONS".equals(method)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String type = req.getParameter("type");
        if ("login".equals(type)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String token;
        // 接受请求头Authorization里面的token
        token = req.getHeader("Authorization");
        System.out.println("Authorization的值:"+token);
        if (token == null) {
            // 接受请求参数里面的token
            token = req.getParameter("token");
            System.out.println("接受请求参数里面的token:"+token);
        }
        if (token == null) {
            Map<String,Object> map = new HashMap<>();
            map.put("msg","未获取到token");
            RespUtil.responseAppJson(resp,map);
            return;
        }
        DecodedJWT verify = JwtUtil.verify(token);
        if (verify == null) {
            Map<String,Object> map = new HashMap<>();
            map.put("msg","校验失败");
            RespUtil.responseAppJson(resp,map);
            return;
        }
        User parse = JwtUtil.parse(verify);
        if (parse == null) {
            Map<String,Object> map = new HashMap<>();
            map.put("msg","解析信息失败");
            RespUtil.responseAppJson(resp,map);
            return;
        }
        System.out.println("拦截器filter里面获取到user对象:"+ JSON.toJSONString(parse));
        filterChain.doFilter(servletRequest, servletResponse);
    }
}

