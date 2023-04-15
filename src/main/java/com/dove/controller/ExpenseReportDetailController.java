package com.dove.controller;

import com.dove.entity.Department;
import com.dove.entity.ExpenseReport;
import com.dove.entity.ExpenseReportDetail;
import com.dove.service.impl.ExpenseReportDetailServiceImpl;
import com.dove.service.impl.ExpenseReportServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报销单细节表(ExpenseReportDetail)控制层
 *
 * @author dove_whispers
 * @date 2023-04-15
 */
@Api(tags = "报销单细节管理模块")
@Controller
@RequestMapping("expenseReportDetail")
public class ExpenseReportDetailController extends BaseController {
    @Resource
    private ExpenseReportServiceImpl expenseReportService;
    @Resource
    private ExpenseReportDetailServiceImpl expenseReportDetailService;

    @ApiOperation(value = "主页跳转至查看报销单细节信息的路由")
    @GetMapping("/goMainExpenseReportDetail")
    public ModelAndView goMainExpenseReportDetail() {
        request.getSession().setAttribute("pageName", "查看报销单信息");
        return new ModelAndView("main-expense-report-detail");
    }
    @ApiOperation(value = "查看报销单细节信息")
    @PostMapping("/queryExpenseReportDetail")
    @ResponseBody
    public Map<String, Object> queryExpenseReportDetail(Integer expensiveId) {
        Map<String, Object> map = new HashMap<>(2);
        List<ExpenseReportDetail> expenseReportDetails;
        try {
            expenseReportDetails = expenseReportDetailService.queryByExpensiveId(expensiveId);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
            return map;
        }
        map.put("success", true);
        map.put("data", expenseReportDetails);
        return map;
    }
}
