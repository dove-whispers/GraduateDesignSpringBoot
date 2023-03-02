package com.dove.controller;

import com.dove.service.impl.ExpenseReportDetailServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 报销单细节表(ExpenseReportDetail)表控制层
 *
 * @author dove_whispers
 * @date 2023-03-02
 */
@Api(tags = "报销单细节管理模块")
@Controller
@RequestMapping("expenseReportDetail")
@Slf4j
public class ExpenseReportDetailController extends BaseController {
    @Resource
    private ExpenseReportDetailServiceImpl expenseReportDetailService;

    @ApiOperation(value = "跳转至新增报销单细节的路由")
    @GetMapping("/toAddExpenseReportDetail")
    public ModelAndView toAddExpenseReportDetail() {
        log.info("进入报销单细节列表");
        request.getSession().setAttribute("pageName", "新增报销单细节");
        return new ModelAndView("expense-report-detail-add");
    }
}

