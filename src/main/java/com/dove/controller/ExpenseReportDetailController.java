package com.dove.controller;

import com.dove.service.impl.ExpenseReportDetailServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 报销单细节表(ExpenseReportDetail)表控制层
 *
 * @author dove_whispers
 * @date 2023-02-27
 */
@Api(tags = "报销单细节管理模块")
@Controller
@RequestMapping
@Slf4j
public class ExpenseReportDetailController extends BaseController {
    @Resource
    private ExpenseReportDetailServiceImpl expenseReportDetailService;
}

