package com.dove.controller;

import cn.hutool.core.date.DateUtil;
import com.dove.dto.EmployeeDTO;
import com.dove.dto.requestDTO.ExpenseReportDetailRequestDTO;
import com.dove.dto.requestDTO.ExpenseReportRequestDTO;
import com.dove.entity.ExpenseReport;
import com.dove.entity.ExpenseReportDetail;
import com.dove.service.impl.EmployeeServiceImpl;
import com.dove.service.impl.ExpenseReportDetailServiceImpl;
import com.dove.service.impl.ExpenseReportServiceImpl;
import com.dove.service.impl.PositionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 报销单表(ExpenseReport)表控制层
 *
 * @author dove_whispers
 * @date 2023-03-02
 */
@Api(tags = "报销单细节管理模块")
@Controller
@RequestMapping("expenseReport")
@Slf4j
public class ExpenseReportController extends BaseController {
    @Resource
    private ExpenseReportServiceImpl expenseReportService;
    @Resource
    private ExpenseReportDetailServiceImpl expenseReportDetailService;
    @Resource
    private EmployeeServiceImpl employeeService;
    @Resource
    private PositionServiceImpl positionService;

    @ApiOperation(value = "跳转至新增报销单的路由")
    @GetMapping("/toAddExpenseReport")
    public ModelAndView toAddExpenseReport() {
        log.info("进入新增报销单页面");
        request.getSession().setAttribute("pageName", "新增报销单");
        return new ModelAndView("expense-report-add");
    }

    @ApiOperation(value = "新增报销单")
    @PostMapping("/addExpenseReport")
    @ResponseBody
    public Map<String, Object> addExpenseReport(@RequestBody ExpenseReportRequestDTO requestDTO) {
        log.info("新增报销单");
        Object userObj = request.getSession().getAttribute("userInfo");
        EmployeeDTO userInfo = (EmployeeDTO) userObj;
        Integer emId = userInfo.getEmId();
        Integer depId = userInfo.getDepId();
        Map<String, Object> map = new HashMap<>(2);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Integer nextDealEmId = employeeService.queryNextDealEmId(emId, depId);
            ExpenseReport expenseReport = new ExpenseReport(null, requestDTO.getCause(), emId, new Date(), DateUtil.nextWeek(), nextDealEmId, requestDTO.getTotalAmount(), "新创建");
            expenseReport = expenseReportService.insert(expenseReport);
            Integer expenseId = expenseReport.getExpenseId();
            for (ExpenseReportDetailRequestDTO expenseReportDetailRequestDTO : requestDTO.getExpenseReportDetails()) {
                String image = expenseReportDetailRequestDTO.getImage();
                expenseReportDetailRequestDTO.setImage("");
                ExpenseReportDetail expenseReportDetail = mapper.readValue(mapper.writeValueAsString(expenseReportDetailRequestDTO), ExpenseReportDetail.class);
                expenseReportDetail.setExpensiveId(expenseId);
                expenseReportDetail.setImage(image.getBytes(StandardCharsets.UTF_8));
                expenseReportDetailService.insert(expenseReportDetail);
            }
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }

}

