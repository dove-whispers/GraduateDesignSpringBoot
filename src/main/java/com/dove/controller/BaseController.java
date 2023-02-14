package com.dove.controller;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 基本控制器
 *
 * @author dove_whispers
 * @date 2023-02-10
 */
@Controller
public class BaseController {
    @Resource
    HttpServletRequest request;
}
