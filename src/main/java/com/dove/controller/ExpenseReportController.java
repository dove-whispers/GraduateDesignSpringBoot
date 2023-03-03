package com.dove.controller;

import com.dove.dto.requestDTO.ExpenseReportRequestDTO;
import com.dove.service.impl.ExpenseReportServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
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
    /**
     * 服务对象
     */
    @Resource
    private ExpenseReportServiceImpl expenseReportService;

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
    public Map<String, Object> addExpenseReport(@RequestBody List<ExpenseReportRequestDTO> requestDTOs) {
        log.info("新增报销单");
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for (ExpenseReportRequestDTO requestDTO : requestDTOs) {
            System.out.println(requestDTO);
            System.out.println("时间" + sdf.format(requestDTO.getTime()));
        }
//        Map<String, Object> map;
//        try {
//            map = departmentService.queryPageList(requestDTO);
//        } catch (Exception e) {
//            map = new HashMap<>(2);
//            map.put("success", false);
//            map.put("errMsg", e.getMessage());
//        }
//        return map;
        return null;
    }

}

