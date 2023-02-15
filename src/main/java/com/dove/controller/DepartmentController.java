package com.dove.controller;

import com.dove.dto.requestDTO.DepartmentListRequestDTO;
import com.dove.dto.requestDTO.ToggleDepartmentRequestDTO;
import com.dove.service.impl.DepartmentServiceImpl;
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
@Slf4j
public class DepartmentController extends BaseController {
    @Resource
    private DepartmentServiceImpl departmentService;

    @ApiOperation(value = "跳转至部门列表的路由")
    @GetMapping("/toList")
    public ModelAndView toList() {
        log.info("进入部门列表");
        request.getSession().setAttribute("pageName", "部门管理");
        return new ModelAndView("department-list");
    }

    @ApiOperation(value = "跳转至新增部门的路由")
    @GetMapping("/toAddDepartment")
    public ModelAndView toAddDepartment() {
        log.info("新增部门");
        request.getSession().setAttribute("pageName", "新增部门");
        return new ModelAndView("department-add");
    }

    @ApiOperation(value = "查询列表数据(包括分页,模糊查询,条件查询)")
    @PostMapping("/getList")
    @ResponseBody
    public Map<String, Object> getList(DepartmentListRequestDTO requestDTO) {
        log.info("查询数据");
        Map<String, Object> map = null;
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
        log.info("修改状态");
        Map<String, Object> map = new HashMap<>(2);
        try {
            if (null == requestDTO) {
                map.put("success", false);
                map.put("errMsg", "空数据错误");
                return map;
            }
            map = departmentService.toggleStatus(requestDTO);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }

    @ApiOperation(value = "新增部门信息")
    @PostMapping("/insertDepartment")
    @ResponseBody
    public Map<String, Object> insertDepartment(@RequestBody DepartmentListRequestDTO requestDTO) {
        log.info("新增部门");
        System.out.println(requestDTO);
        Map<String, Object> map = new HashMap<>(2);
        try {
            if (null == requestDTO) {
                map.put("success", false);
                map.put("errMsg", "空数据错误");
                return map;
            }
            map.put("success",true);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }
}
