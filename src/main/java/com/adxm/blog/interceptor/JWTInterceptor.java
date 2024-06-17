package com.adxm.blog.interceptor;

import com.adxm.blog.utils.JWTUtils;
import com.adxm.blog.utils.R;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @Author: Adxm
 * @Date: 2022/10/3
 * @Description:
 */
@Component
public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 若options请求，则放行
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        } else {
            // 若token有效，则向请求头追加用户基本信息
            Map<String, Object> map = JWTUtils.verifyToken(request.getHeader("token"));
            if (!map.isEmpty()) {
                // 向请求头追加用户信息
                request.setAttribute("userId", map.get("userId"));
                request.setAttribute("username", map.get("username"));
                return true;
            } else {
                // token无效，则返回认证失败状态
                R r = R.unAuthenticate().msg("认证失败，请重新登录");
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                try {
                    response.getWriter().print(Jackson2ObjectMapperBuilder.json().build().writeValueAsString(r));
                } catch (Exception e) {
                    System.out.println("拦截器 IO异常， 错误：" + e);
                } finally {
                    response.getWriter().close();
                }
                return false;
            }
        }
    }
}
