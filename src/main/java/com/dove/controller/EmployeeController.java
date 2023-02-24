package com.dove.controller;

import com.dove.dto.EmployeeDTO;
import com.dove.dto.requestDTO.DepartmentInfoRequestDTO;
import com.dove.dto.requestDTO.EmployeeInfoRequestDTO;
import com.dove.dto.requestDTO.EmployeeListRequestDTO;
import com.dove.entity.Department;
import com.dove.entity.Employee;
import com.dove.service.impl.EmployeeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
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
 * 员工控制器
 *
 * @author dove_whispers
 * @date 2023-02-20
 */
@Api(tags = "员工管理模块")
@Controller
@RequestMapping("/employee")
@Slf4j
public class EmployeeController extends BaseController {
    @Resource
    private EmployeeServiceImpl employeeService;

    @ApiOperation(value = "跳转至员工列表的路由")
    @GetMapping("/toList")
    public ModelAndView toList() {
        log.info("进入员工列表");
        request.getSession().setAttribute("pageName", "员工管理");
        return new ModelAndView("employee-list");
    }

    @ApiOperation(value = "跳转至新增员工的路由")
    @GetMapping("/toAddEmployee")
    public ModelAndView toAddEmployee() {
        log.info("新增员工");
        request.getSession().setAttribute("pageName", "新增员工");
        return new ModelAndView("employee-add");
    }

    @ApiOperation(value = "跳转至查看员工信息的路由")
    @GetMapping("/goEmployee")
    public ModelAndView goEmployee() {
        log.info("查看员工信息");
        request.getSession().setAttribute("pageName", "员工详情");
        return new ModelAndView("employee-info");
    }

    @ApiOperation(value = "跳转至查看员工信息的路由")
    @GetMapping("/goEmployeeEdit")
    public ModelAndView goEmployeeEdit() {
        log.info("修改员工信息");
        request.getSession().setAttribute("pageName", "修改员工信息");
        return new ModelAndView("employee-edit");
    }

    @ApiOperation(value = "查询列表数据(包括分页,模糊查询,条件查询)")
    @PostMapping("/getList")
    @ResponseBody
    public Map<String, Object> getList(@RequestBody EmployeeListRequestDTO requestDTO) {
        log.info("查询员工数据");
        Map<String, Object> map;
        try {
            map = employeeService.queryPageList(requestDTO);
        } catch (Exception e) {
            map = new HashMap<>(2);
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }
    @ApiOperation(value = "处理(新增或修改)员工信息")
    @PostMapping("/operateEmployee")
    @ResponseBody
    public Map<String, Object> operateEmployee(@RequestBody EmployeeInfoRequestDTO requestDTO) {
        Map<String, Object> map = new HashMap<>(2);
        try {
            if (null == requestDTO) {
                map.put("success", false);
                map.put("errMsg", "员工数据不能为空");
                return map;
            }
            ObjectMapper mapper = new ObjectMapper();
            //默认密码
            requestDTO.setPassword("123456");
            if (null == requestDTO.getEmId()) {
                employeeService.insert(mapper.readValue(mapper.writeValueAsString(requestDTO), Employee.class));
            } else {
                employeeService.update(mapper.readValue(mapper.writeValueAsString(requestDTO), Employee.class));
            }
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }

    @ApiOperation(value = "查看员工信息")
    @PostMapping("/queryEmployee")
    @ResponseBody
    public Map<String, Object> queryDepartment(Integer emId) {
        log.info("查看员工信息");
        Map<String, Object> map = new HashMap<>(2);
        Employee employee;
        try {
            if (null == emId) {
                map.put("success", false);
                map.put("errMsg", "员工id不能为空");
                return map;
            }
            employee = employeeService.queryById(emId);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
            return map;
        }
        map.put("success", true);
        map.put("data", employee);
        return map;
    }
}