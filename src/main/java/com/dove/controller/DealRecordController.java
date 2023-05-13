package com.dove.controller;

import com.dove.dto.EmployeeDTO;
import com.dove.dto.requestDTO.AuditDealRecordRequestDTO;
import com.dove.service.impl.DealRecordServiceImpl;
import com.dove.service.impl.EmployeeServiceImpl;
import com.dove.service.impl.ExpenseReportServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 操作记录表(DealRecord)表控制层
 *
 * @author dove_whispers
 * @date 2023-02-27
 */
@Api(tags = "操作记录管理模块")
@Controller
@RequestMapping("/dealRecord")
public class DealRecordController extends BaseController {
    @Resource
    private DealRecordServiceImpl dealRecordService;
    @Resource
    private EmployeeServiceImpl employeeService;
    @Resource
    private ExpenseReportServiceImpl expenseReportService;

    @ApiOperation(value = "获取报销单进度")
    @PostMapping("/queryExpenseReportStep")
    @ResponseBody
    public Map<String, Object> queryExpenseReportStep(Integer expensiveId) {
        Map<String, Object> map = new HashMap<>(2);
        try {
            map.put("success", true);
            map.put("step", dealRecordService.queryExpenseReportStep(expensiveId));
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }

    @ApiOperation(value = "获取报销单最新处理记录")
    @PostMapping("/queryLatestDealRecord")
    @ResponseBody
    public Map<String, Object> queryLatestDealRecord(Integer expensiveId) {
        Map<String, Object> map = new HashMap<>(2);
        try {
            map.put("success", true);
            map.put("data", dealRecordService.queryLatestDealRecord(expensiveId));
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }

    @ApiOperation(value = "新增报销单处理记录")
    @PostMapping("/addAuditDeal")
    @ResponseBody
    public Map<String, Object> addAuditDeal(@RequestBody AuditDealRecordRequestDTO requestDTO) {
        Map<String, Object> map = new HashMap<>(2);
        EmployeeDTO userInfo = (EmployeeDTO) request.getSession().getAttribute("userInfo");
        String way = requestDTO.getWay();
        String pass = "通过";
        String repulse = "打回";
        String terminate = "终止";
        Integer nextDealEmId = null;
        if (pass.equals(way)) {
            nextDealEmId = employeeService.queryNextDealEmId(userInfo.getEmId(), userInfo.getDepId());
        } else if (repulse.equals(way)) {
            nextDealEmId = dealRecordService.queryExpensiveLatestDeal(requestDTO.getExpenseId()).getEmId();
        } else if (terminate.equals(way)) {
            nextDealEmId = 0;
        }
        try {
            dealRecordService.addAuditRecord(requestDTO, userInfo, nextDealEmId);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }
}

