package com.dove.interceptor;

import com.dove.dto.EmployeeDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 *
 * @author dove_whispers
 * @date 2023-02-12
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        EmployeeDTO userInfo = (EmployeeDTO) request.getSession().getAttribute("userInfo");
        if (null != userInfo) {
            if (userInfo.getEmId() != null && 1 == userInfo.getStatus()) {
                if (request.getRequestURI().toLowerCase().contains("login")) {
                    response.sendRedirect("/main");
                    return false;
                }
                return true;
            }
        }
        if (request.getRequestURI().toLowerCase().contains("login")) {
            return true;
        }
        response.sendRedirect("/login");
        return false;
    }
}