package com.dove.controller;

import com.dove.constants.Constants;
import com.dove.dto.EmployeeDTO;
import com.dove.dto.requestDTO.DealRecordRequestDTO;
import com.dove.entity.DealRecord;
import com.dove.service.impl.DealRecordServiceImpl;
import com.dove.service.impl.EmployeeServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
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
@Slf4j
public class DealRecordController extends BaseController {
    @Resource
    private DealRecordServiceImpl dealRecordService;
    @Resource
    private EmployeeServiceImpl employeeService;

    @ApiOperation(value = "获取报销单进度")
    @PostMapping("/queryExpenseReportStep")
    @ResponseBody
    public Map<String, Object> queryExpenseReportStep(Integer expensiveId) {
        Map<String, Object> map = new HashMap<>(2);
        try {
            Integer integer = dealRecordService.queryExpenseReportStep(expensiveId);
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
    @PostMapping("/addNewDeal")
    @ResponseBody
    public Map<String, Object> addNewDeal(@RequestBody DealRecordRequestDTO requestDTO) {
        Map<String, Object> map = new HashMap<>(2);
        EmployeeDTO userInfo = (EmployeeDTO) request.getSession().getAttribute("userInfo");
        String positionName = userInfo.getPosition().getPositionName();
        String way = requestDTO.getWay();
        String dealResult = null;
        String status = null;
        try {
            if ("通过".equals(way)) {
                if (Constants.Name.FINANCIAL_SUPERVISOR.equals(positionName)) {
                    dealResult = Constants.Result.REMITTANCE;
                    status = Constants.Status.PAID;
                } else if (Constants.Name.GENERAL_MANAGER.equals(positionName)) {
                    dealResult = Constants.Result.PASS;
                    status = Constants.Status.TO_BE_FINANCE_REVIEWED;
                } else if (Constants.Name.DEPARTMENT_MANAGER.equals(positionName)) {
                    dealResult = Constants.Result.PASS;
                    status = Constants.Status.TO_BE_GM_REVIEWED;
                }
            } else if ("打回".equals(way)) {
                dealResult = Constants.Result.REPULSE;
                status = Constants.Status.TO_BE_MODIFIED;
            } else if ("终止".equals(way)) {
                dealResult = Constants.Result.TERMINATED;
                status = Constants.Status.TERMINATED;
            }
            DealRecord dealRecord = new DealRecord(null, requestDTO.getExpenseId(), userInfo.getEmId(), new Date(), Constants.Way.AUDIT, dealResult, requestDTO.getComment());
            Integer nextDealEmId = employeeService.queryNextDealEmId(userInfo.getEmId(), userInfo.getDepId());
            if ("终止".equals(way)) {
                nextDealEmId = 0;
            }
            dealRecordService.addNewDeal(dealRecord, nextDealEmId, status);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }
}

