package com.dove.controller;

import com.dove.service.impl.ExpenseReportServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 报销单表(ExpenseReport)表控制层
 *
 * @author makejava
 * @since 2023-02-27 16:30:07
 */
@Api(tags = "报销单管理模块")
@Controller
@RequestMapping("expenseReport")
@Slf4j
public class ExpenseReportController extends BaseController {
    @Resource
    private ExpenseReportServiceImpl expenseReportService;

    @ApiOperation(value = "跳转至新增报销单的路由")
    @GetMapping("/toAddExpenseReport")
    public ModelAndView toAddExpenseReport() {
        log.info("进入报销单列表");
        request.getSession().setAttribute("pageName", "新增报销单");
        return new ModelAndView("expense-report-add");
    }
}

