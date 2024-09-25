package com.example.bookmanagement.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.example.bookmanagement.service.AccountService;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

public class JwtAuthenticationInterceptor implements HandlerInterceptor {
    @Autowired(required = false)
    AccountService accountService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            Object object) throws Exception {
        // 从请求头中取出 token 这里需要和前端约定好把jwt放到请求头一个叫token的地方
        String token = "";
        if (!StringUtils.isEmpty(httpServletRequest.getParameter("token"))) {
            token = httpServletRequest.getParameter("token");
        } else if (!StringUtils.isEmpty(httpServletRequest.getHeader("token"))) {
            token = httpServletRequest.getHeader("token");
        }
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        // 检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        // 默认全部检查
        else {
            System.out.println("被JWT拦截需要验证");
            // 执行认证
            if (!StringUtils.isEmpty(token)) {
                Boolean verify = JwtUtils.verifyToken(token);
                if (verify) {
                    // 获取载荷内容
                    String userName = JwtUtils.getClaimByName(token, "userName").asString();
                    String email = JwtUtils.getClaimByName(token, "email").asString();
                    // 放入attribute以便后面调用
                    httpServletRequest.setAttribute("userName", userName);
                    httpServletRequest.setAttribute("email", email);
                    return true;
                }
            }
        }
        TokenInfo info = new TokenInfo("", 403, "token认证失败");
        returnJson(httpServletResponse, JSONObject.toJSONString(info));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
            ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            Object o, Exception e) throws Exception {
    }

    private void returnJson(HttpServletResponse response, String json) throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
            System.out.println("response error");
        } finally {
            if (writer != null)
                writer.close();
        }
    }

}
