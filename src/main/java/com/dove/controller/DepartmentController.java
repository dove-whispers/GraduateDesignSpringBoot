package com.dove.controller;

import com.dove.dto.requestDTO.DepartmentInfoRequestDTO;
import com.dove.dto.requestDTO.DepartmentListRequestDTO;
import com.dove.dto.requestDTO.ToggleDepartmentRequestDTO;
import com.dove.entity.Department;
import com.dove.service.impl.DepartmentServiceImpl;
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
 * 部门控制器
 *
 * @author dove_whispers
 * @date 2023-02-13
 */
@Api(tags = "部门管理模块")
@Controller
@RequestMapping("/department")
public class DepartmentController extends BaseController {
    @Resource
    private DepartmentServiceImpl departmentService;

    @ApiOperation(value = "跳转至部门列表的路由")
    @GetMapping("/toList")
    public ModelAndView toList() {
        request.getSession().setAttribute("pageName", "部门管理");
        return new ModelAndView("department-list");
    }

    @ApiOperation(value = "跳转至新增部门的路由")
    @GetMapping("/toAddDepartment")
    public ModelAndView toAddDepartment() {
        request.getSession().setAttribute("pageName", "新增部门");
        return new ModelAndView("department-add");
    }

    @ApiOperation(value = "跳转至查看部门信息的路由")
    @GetMapping("/goDepartment")
    public ModelAndView goDepartment() {
        request.getSession().setAttribute("pageName", "部门详情");
        return new ModelAndView("department-info");
    }

    @ApiOperation(value = "跳转至查看部门信息的路由")
    @GetMapping("/goDepartmentEdit")
    public ModelAndView goDepartmentEdit() {
        request.getSession().setAttribute("pageName", "修改部门信息");
        return new ModelAndView("department-edit");
    }

    @ApiOperation(value = "查询列表数据(包括分页,模糊查询,条件查询)")
    @PostMapping("/getList")
    @ResponseBody
    public Map<String, Object> getList(@RequestBody DepartmentListRequestDTO requestDTO) {
        Map<String, Object> map;
        try {
            map = departmentService.queryPageList(requestDTO);
        } catch (Exception e) {
            map = new HashMap<>(2);
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }

    @ApiOperation(value = "对单个部门状态进行切换")
    @PostMapping("/toggleDepartmentStatus")
    @ResponseBody
    public Map<String, Object> toggleDepartmentStatus(@RequestBody ToggleDepartmentRequestDTO requestDTO) {
        Map<String, Object> map = new HashMap<>(2);
        try {
            if (null == requestDTO) {
                map.put("success", false);
                map.put("errMsg", "状态丢失");
                return map;
            }
            map = departmentService.toggleStatus(requestDTO);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }

    @ApiOperation(value = "处理(新增或修改)部门信息")
    @PostMapping("/operateDepartment")
    @ResponseBody
    public Map<String, Object> operateDepartment(@RequestBody DepartmentInfoRequestDTO requestDTO) {
        Map<String, Object> map = new HashMap<>(2);
        try {
            if (null == requestDTO) {
                map.put("success", false);
                map.put("errMsg", "部门数据不能为空");
                return map;
            }
            ObjectMapper mapper = new ObjectMapper();
            if (null == requestDTO.getDepId()) {
                departmentService.insert(mapper.readValue(mapper.writeValueAsString(requestDTO), Department.class));
            } else {
                departmentService.update(mapper.readValue(mapper.writeValueAsString(requestDTO), Department.class));
            }
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }

    @ApiOperation(value = "查看部门信息")
    @PostMapping("/queryDepartment")
    @ResponseBody
    public Map<String, Object> queryDepartment(Integer departmentId) {
        Map<String, Object> map = new HashMap<>(2);
        Department department;
        try {
            if (null == departmentId) {
                map.put("success", false);
                map.put("errMsg", "部门id不能为空");
                return map;
            }
            department = departmentService.queryById(departmentId);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
            return map;
        }
        map.put("success", true);
        map.put("data", department);
        return map;
    }

    @ApiOperation(value = "查询status=1的列表数据")
    @PostMapping("/queryActiveDepartmentList")
    @ResponseBody
    public Map<String, Object> queryActiveDepartmentList() {
        Map<String, Object> map;
        try {
            map = departmentService.queryActiveDepartmentList();
        } catch (Exception e) {
            map = new HashMap<>(2);
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }
}
