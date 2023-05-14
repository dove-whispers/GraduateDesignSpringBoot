package com.dove.controller;

import com.dove.dto.EmployeeDTO;
import com.dove.dto.requestDTO.*;
import com.dove.entity.Department;
import com.dove.entity.ExpenseReport;
import com.dove.entity.Position;
import com.dove.service.impl.EmployeeServiceImpl;
import com.dove.service.impl.ExpenseReportDetailServiceImpl;
import com.dove.service.impl.ExpenseReportServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 报销单表(ExpenseReport)控制层
 *
 * @author dove_whispers
 * @date 2023-03-02
 */
@Api(tags = "报销单管理模块")
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

    @ApiOperation(value = "跳转至新增报销单的路由")
    @GetMapping("/toAddExpenseReport")
    public ModelAndView toAddExpenseReport() {
        request.getSession().setAttribute("pageName", "新增报销单");
        return new ModelAndView("expense-report-add");
    }

    @ApiOperation(value = "跳转至查看报销单的路由")
    @GetMapping("/toViewExpenseReport")
    public ModelAndView toViewExpenseReport() {
        request.getSession().setAttribute("pageName", "审核报销单");
        return new ModelAndView("expense-report-view");
    }

    @ApiOperation(value = "报销单查重")
    @PostMapping("/checkExist")
    @ResponseBody
    public boolean checkExist(@RequestBody ExpenseReportCheckRequestDTO requestDTO) {
        return expenseReportDetailService.checkExist(requestDTO);
    }

    @ApiOperation(value = "新增报销单")
    @PostMapping("/addExpenseReport")
    @ResponseBody
    public Map<String, Object> addExpenseReport(@RequestBody ExpenseReportAddRequestDTO requestDTO) {
        EmployeeDTO userInfo = (EmployeeDTO) request.getSession().getAttribute("userInfo");
        Map<String, Object> map = new HashMap<>(2);
        ExpenseReportCheckRequestDTO c = new ExpenseReportCheckRequestDTO();
        try {
            int count = 1;
            for (ExpenseReportDetailRequestDTO expenseReportDetailRequestDTO : requestDTO.getExpenseReportDetails()) {
                c.setCode(expenseReportDetailRequestDTO.getCode());
                c.setNum(expenseReportDetailRequestDTO.getNum());
                if (expenseReportDetailService.checkExist(c)) {
                    map.put("success", false);
                    map.put("errCount", count);
                    return map;
                }
                count++;
            }
            Integer emId = userInfo.getEmId();
            Integer nextDealEmId = employeeService.queryNextDealEmId(emId, userInfo.getDepId());
            expenseReportService.addExpenseReport(requestDTO, emId, nextDealEmId);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
        }
        return map;
    }

    @ApiOperation(value = "修改报销单")
    @PostMapping("/updateExpenseReport")
    @ResponseBody
    public Map<String, Object> updateExpenseReport(@RequestBody ExpenseReportUpdateRequestDTO requestDTO) {
        EmployeeDTO userInfo = (EmployeeDTO) request.getSession().getAttribute("userInfo");
        Map<String, Object> map = new HashMap<>(1);
        try {
            Integer nextDealEmId = employeeService.queryNextDealEmId(userInfo.getEmId(), userInfo.getDepId());
            expenseReportService.updateReport(userInfo, requestDTO, nextDealEmId);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
        }
        return map;
    }

    @ApiOperation(value = "获取主页报销单列表")
    @PostMapping("/getMainList")
    @ResponseBody
    public Map<String, Object> getMainList(@RequestBody ExpenseReportMainListRequestDTO requestDTO) {
        Object userObj = request.getSession().getAttribute("userInfo");
        EmployeeDTO userInfo = (EmployeeDTO) userObj;
        Integer emId = userInfo.getEmId();
        Department department = userInfo.getDepartment();
        Position position = userInfo.getPosition();
        Map<String, Object> map = new HashMap<>(2);
        try {
            map = expenseReportService.queryMainPageList(requestDTO, emId, department, position);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }

    @ApiOperation(value = "获取报销单列表")
    @PostMapping("/getViewList")
    @ResponseBody
    public Map<String, Object> getViewList(@RequestBody ExpenseReportViewListRequestDTO requestDTO) {
        EmployeeDTO userInfo = (EmployeeDTO) request.getSession().getAttribute("userInfo");
        Integer emId = userInfo.getEmId();
        Map<String, Object> map = new HashMap<>(2);
        try {
            map = expenseReportService.queryViewPageList(requestDTO, emId);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }

    @ApiOperation(value = "查询报销单是否处于修改状态")
    @PostMapping("/queryNeedDisable")
    @ResponseBody
    public Map<String, Object> queryNeedDisable(Integer expenseId) {
        EmployeeDTO userInfo = (EmployeeDTO) request.getSession().getAttribute("userInfo");
        Map<String, Object> map = new HashMap<>(2);
        try {
            ExpenseReport expenseReport = expenseReportService.queryById(expenseId);
            map.put("success", true);
            map.put("data", expenseReport.getNextDealEm().equals(userInfo.getEmId()));
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }

    @ApiOperation(value = "放弃报销单")
    @PostMapping("/abandonReport")
    @ResponseBody
    public Map<String, Object> abandonReport(Integer expensiveId, String comment) {
        EmployeeDTO userInfo = (EmployeeDTO) request.getSession().getAttribute("userInfo");
        Map<String, Object> map = new HashMap<>(1);
        try {
            expenseReportService.abortReport(userInfo, expensiveId,comment);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
        }
        return map;
    }

    @ApiOperation(value = "是否在处理申请人本人的报销单")
    @PostMapping("/isSelfReport")
    @ResponseBody
    public Map<String, Object> isSelfReport(Integer expensiveId) {
        EmployeeDTO userInfo = (EmployeeDTO) request.getSession().getAttribute("userInfo");
        Map<String, Object> map = new HashMap<>(2);
        ExpenseReport expenseReport;
        try {
            expenseReport = expenseReportService.queryById(expensiveId);
            map.put("success", true);
            map.put("outcome", expenseReport.getEmId().equals(userInfo.getEmId()));
        } catch (Exception e) {
            map.put("success", false);
        }
        return map;
    }
}

