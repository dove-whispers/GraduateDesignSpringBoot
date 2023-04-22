package com.dove.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 权限管理控制器
 *
 * @author dove_whispers
 * @date 2023-02-27
 */
@Api(tags = "权限管理模块")
@Controller
@RequestMapping("/authority")
@Slf4j
public class AuthorityController extends BaseController {
    @ApiOperation(value = "跳转至查看部门信息的路由")
    @GetMapping("/goPage")
    public ModelAndView goDepartment() {
        request.getSession().setAttribute("pageName", "设置权限");
        return new ModelAndView("authorities");
    }
}

