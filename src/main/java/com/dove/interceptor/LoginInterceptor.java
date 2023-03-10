package com.dove.interceptor;

import com.dove.dto.EmployeeDTO;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("被拦截的URI:" + request.getRequestURI().toLowerCase());
        Object userObj = request.getSession().getAttribute("userInfo");
        if (null != userObj) {
            EmployeeDTO userInfo = (EmployeeDTO) userObj;
            if (userInfo.getEmId() != null && userInfo.getStatus() == 1) {
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