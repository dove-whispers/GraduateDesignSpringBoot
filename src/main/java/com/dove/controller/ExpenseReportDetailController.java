package com.dove.controller;

import com.dove.dto.responseDTO.ExpenseReportDetailOptimized;
import com.dove.dto.responseDTO.ExpenseReportDetailResponseDTO;
import com.dove.entity.ExpenseReport;
import com.dove.entity.ExpenseReportDetail;
import com.dove.service.impl.ExpenseReportDetailServiceImpl;
import com.dove.service.impl.ExpenseReportServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
    @SneakyThrows
    @PostMapping("/queryExpenseReportDetail")
    @ResponseBody
    public Map<String, Object> queryExpenseReportDetail(Integer expensiveId) {
        ExpenseReport expenseReport = expenseReportService.queryById(expensiveId);
        String cause = expenseReport.getCause();
        BigDecimal totalAmount = expenseReport.getTotalAmount();
        Map<String, Object> map = new HashMap<>(4);
        ObjectMapper mapper = new ObjectMapper();
        List<ExpenseReportDetail> expenseReportDetails;
        List<ExpenseReportDetailOptimized> expenses = new ArrayList<>(5);
        try {
            expenseReportDetails = expenseReportDetailService.queryByExpensiveId(expensiveId);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
            return map;
        }
        for (ExpenseReportDetail expenseReportDetail : expenseReportDetails) {
            String image = new String(expenseReportDetail.getImage(), StandardCharsets.UTF_8);
            expenseReportDetail.setImage(null);
            ExpenseReportDetailOptimized e = mapper.readValue(mapper.writeValueAsString(expenseReportDetail), ExpenseReportDetailOptimized.class);
            e.setImage(image);
            expenses.add(e);
        }
        ExpenseReportDetailResponseDTO responseDTO = new ExpenseReportDetailResponseDTO(expenses, cause, totalAmount);
        map.put("success", true);
        map.put("data", responseDTO);
        return map;
    }
}
