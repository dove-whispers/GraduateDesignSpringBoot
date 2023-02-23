package com.dove.controller;

import cn.hutool.core.util.StrUtil;
import com.dove.dto.EmployeeDTO;
import com.dove.dto.requestDTO.LoginRequestDTO;
import com.dove.service.impl.EmployeeServiceImpl;
import com.dove.util.CodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 首页控制器
 *
 * @author dove_whispers
 * @date 2022/01/12
 */
@Api(tags = "登录登出")
@RestController
@RequestMapping
@Slf4j
public class IndexController extends BaseController {
    @Resource
    private EmployeeServiceImpl employeeService;

    @ApiOperation(value = "跳转至登陆页面")
    @RequestMapping("/login")
    public ModelAndView login() {
        log.info("用户登录");
        return new ModelAndView("login");
    }

    @ApiOperation(value = "登陆验证")
    @PostMapping("/checkLogin")
    @ResponseBody
    public Map<String, Object> checkLogin(@RequestBody LoginRequestDTO loginRequestDTO) {
        Map<String, Object> resultMap = new HashMap<>(2);
        if (loginRequestDTO.getNeedVerify() && !CodeUtil.checkVerifyCode(request, loginRequestDTO.getVerifyCodeActual())) {
            log.info("用户验证码错误");
            resultMap.put("success", false);
            resultMap.put("errorMsg", "验证码错误");
            return resultMap;
        }
        String username = loginRequestDTO.getUserName();
        String password = loginRequestDTO.getPassword();
        if (!StrUtil.isEmpty(username) && !StrUtil.isEmpty(password)) {
            EmployeeDTO userInfo = employeeService.checkUserByUserNameAndPassword(username, password);
            if (null != userInfo) {
                log.info("用户" + userInfo.getName() + "登录了");
                resultMap.put("success", true);
                resultMap.put("username", userInfo.getName());
                request.getSession().setAttribute("userInfo", userInfo);
            } else {
                log.info("一次错误的登录");
                resultMap.put("success", false);
                resultMap.put("errorMsg", "用户名密码错误");
            }
        } else {
            log.info("一次空的登录");
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
    public Map<String, Object> logOut() {
        log.info("用户退出");
        Map<String, Object> modelMap = new HashMap<>(2);
        request.getSession().setAttribute("userInfo", null);
        modelMap.put("success", true);
        return modelMap;
    }
}
