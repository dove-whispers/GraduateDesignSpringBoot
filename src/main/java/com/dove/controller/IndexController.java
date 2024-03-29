package com.dove.controller;

import cn.hutool.core.util.StrUtil;
import com.dove.dto.EmployeeDTO;
import com.dove.dto.requestDTO.LoginRequestDTO;
import com.dove.dto.requestDTO.PasswordRequestDTO;
import com.dove.entity.Employee;
import com.dove.service.impl.EmployeeServiceImpl;
import com.dove.util.CodeUtil;
import com.dove.util.MD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 首页控制器
 *
 * @author dove_whispers
 * @date 2022/01/12
 */
@Api(tags = "登录登出")
@Controller
@RequestMapping
public class IndexController extends BaseController {
    @Resource
    private EmployeeServiceImpl employeeService;

    @ApiOperation(value = "跳转至登陆页面")
    @RequestMapping("/login")
    @SneakyThrows
    public ModelAndView login(@CookieValue(value = "username", required = false) String username, @CookieValue(value = "password", required = false) String password) {
        if (!StrUtil.isEmpty(username) && !StrUtil.isEmpty(password)) {
            EmployeeDTO userInfo = employeeService.checkUserByUserNameAndMdPassword(username, password);
            if (null != userInfo && 1 == userInfo.getStatus()) {
                request.getSession().setAttribute("userInfo", userInfo);
                return new ModelAndView("main");
            }
            Cookie cookie1 = new Cookie("username", null);
            Cookie cookie2 = new Cookie("password", null);
            cookie1.setMaxAge(0);
            cookie2.setMaxAge(0);
            response.addCookie(cookie1);
            response.addCookie(cookie2);
        }
        return new ModelAndView("login");
    }

    @ApiOperation(value = "登陆验证")
    @PostMapping("/checkLogin")
    @ResponseBody
    public Map<String, Object> checkLogin(@RequestBody LoginRequestDTO loginRequestDTO) {
        Map<String, Object> resultMap = new HashMap<>(2);
        if (!CodeUtil.checkVerifyCode(request, loginRequestDTO.getVerifyCodeActual())) {
            resultMap.put("success", false);
            resultMap.put("errorMsg", "验证码错误");
            return resultMap;
        }
        String username = loginRequestDTO.getUserName();
        String password = loginRequestDTO.getPassword();
        if (!StrUtil.isEmpty(username) && !StrUtil.isEmpty(password)) {
            EmployeeDTO userInfo = employeeService.checkUserByUserNameAndPassword(username, password);
            if (null != userInfo) {
                if (1 != userInfo.getStatus()) {
                    resultMap.put("success", false);
                    resultMap.put("errorMsg", "该用户已离职!");
                } else {
                    resultMap.put("success", true);
                    resultMap.put("username", userInfo.getName());
                    if (loginRequestDTO.getIsSave()) {
                        Cookie cookie1 = new Cookie("username", username);
                        Cookie cookie2 = new Cookie("password", MD5Util.getMD5(password));
                        cookie1.setMaxAge((int) TimeUnit.SECONDS.convert(5, TimeUnit.DAYS));
                        cookie2.setMaxAge((int) TimeUnit.SECONDS.convert(5, TimeUnit.DAYS));
                        response.addCookie(cookie1);
                        response.addCookie(cookie2);
                    }
                    request.getSession().setAttribute("userInfo", userInfo);
                }
            } else {
                resultMap.put("success", false);
                resultMap.put("errorMsg", "用户名密码错误!");
            }
        } else {
            resultMap.put("success", false);
            resultMap.put("errorMsg", "用户名密码均不能为空");
        }
        return resultMap;
    }

    @ApiOperation(value = "跳转主页面")
    @RequestMapping("/main")
    public ModelAndView toMain() {
        request.getSession().setAttribute("pageName", "后台首页");
        return new ModelAndView("main");
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    @ResponseBody
    public Map<String, Object> logOut() {
        Map<String, Object> modelMap = new HashMap<>(1);
        Cookie cookie1 = new Cookie("username", null);
        Cookie cookie2 = new Cookie("password", null);
        cookie1.setMaxAge(0);
        cookie2.setMaxAge(0);
        response.addCookie(cookie1);
        response.addCookie(cookie2);
        request.getSession().setAttribute("userInfo", null);
        modelMap.put("success", true);
        return modelMap;
    }

    @ApiOperation(value = "跳转修改密码")
    @RequestMapping("/editPwdPage")
    public ModelAndView editPwdPage() {
        request.getSession().setAttribute("pageName", "修改密码");
        return new ModelAndView("editPwdPage");
    }

    @ApiOperation(value = "查询用户密码")
    @PostMapping("/checkPassword")
    @ResponseBody
    public Map<String, Object> checkPassword(@RequestBody PasswordRequestDTO passwordRequestDTO) {
        Map<String, Object> modelMap = new HashMap<>(2);
        EmployeeDTO userInfo = (EmployeeDTO) request.getSession().getAttribute("userInfo");
        Employee employee = employeeService.queryById(userInfo.getEmId());
        String realPassword = employee.getPassword();
        String oldPassword = passwordRequestDTO.getOldPassword();
        String newPassword = passwordRequestDTO.getNewPassword();
        if (realPassword.equals(MD5Util.getMD5(oldPassword))) {
            if (oldPassword.equalsIgnoreCase(newPassword)) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "旧密码不能与新密码相同!");
            } else {
                modelMap.put("success", true);
                employee.setPassword(MD5Util.getMD5(newPassword));
                employeeService.update(employee);
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "旧密码错误!");
        }
        return modelMap;
    }
}
