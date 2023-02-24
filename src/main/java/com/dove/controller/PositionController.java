package com.dove.controller;

import com.dove.dto.requestDTO.PositionInfoRequestDTO;
import com.dove.dto.requestDTO.PositionListRequestDTO;
import com.dove.dto.requestDTO.TogglePositionRequestDTO;
import com.dove.entity.Position;
import com.dove.service.impl.PositionServiceImpl;
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
 * 位置控制器
 *
 * @author dove_whispers
 * @date 2023-02-19
 */
@Api(tags = "职位管理模块")
@Controller
@RequestMapping("/position")
@Slf4j
public class PositionController extends BaseController {
    @Resource
    private PositionServiceImpl positionService;

    @ApiOperation(value = "跳转至职位列表的路由")
    @GetMapping("/toList")
    public ModelAndView toList() {
        log.info("进入职位列表");
        request.getSession().setAttribute("pageName", "职位管理");
        return new ModelAndView("position-list");
    }

    @ApiOperation(value = "跳转至新增职位的路由")
    @GetMapping("/toAddPosition")
    public ModelAndView toAddPosition() {
        log.info("新增职位");
        request.getSession().setAttribute("pageName", "新增职位");
        return new ModelAndView("position-add");
    }

    @ApiOperation(value = "跳转至查看职位信息的路由")
    @GetMapping("/goPosition")
    public ModelAndView goPosition() {
        log.info("查看职位信息");
        request.getSession().setAttribute("pageName", "职位详情");
        return new ModelAndView("position-info");
    }

    @ApiOperation(value = "跳转至查看职位信息的路由")
    @GetMapping("/goPositionEdit")
    public ModelAndView goPositionEdit() {
        log.info("修改职位信息");
        request.getSession().setAttribute("pageName", "修改职位信息");
        return new ModelAndView("position-edit");
    }

    @ApiOperation(value = "查询列表数据(包括分页,模糊查询,条件查询)")
    @PostMapping("/getList")
    @ResponseBody
    public Map<String, Object> getList(@RequestBody PositionListRequestDTO requestDTO) {
        log.info("查询职位数据");
        Map<String, Object> map;
        try {
            map = positionService.queryPageList(requestDTO);
        } catch (Exception e) {
            map = new HashMap<>(2);
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }

    @ApiOperation(value = "对单个职位状态进行切换")
    @PostMapping("/togglePositionStatus")
    @ResponseBody
    public Map<String, Object> togglePositionStatus(@RequestBody TogglePositionRequestDTO requestDTO) {
        log.info("修改职位状态");
        Map<String, Object> map = new HashMap<>(2);
        try {
            if (null == requestDTO) {
                map.put("success", false);
                map.put("errMsg", "状态丢失");
                return map;
            }
            map = positionService.toggleStatus(requestDTO);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }

    @ApiOperation(value = "处理(新增或修改)职位信息")
    @PostMapping("/operatePosition")
    @ResponseBody
    public Map<String, Object> operatePosition(@RequestBody PositionInfoRequestDTO requestDTO) {
        Map<String, Object> map = new HashMap<>(2);
        try {
            if (null == requestDTO) {
                map.put("success", false);
                map.put("errMsg", "职位数据不能为空");
                return map;
            }
            ObjectMapper mapper = new ObjectMapper();
            Position position = mapper.readValue(mapper.writeValueAsString(requestDTO), Position.class);
            if (null == requestDTO.getPositionId()) {
                positionService.insert(position);
            } else {
                positionService.update(position);
            }
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }

    @ApiOperation(value = "查看职位信息")
    @PostMapping("/queryPosition")
    @ResponseBody
    public Map<String, Object> queryPosition(Integer positionId) {
        log.info("查看职位");
        Map<String, Object> map = new HashMap<>(2);
        Position position;
        try {
            if (null == positionId) {
                map.put("success", false);
                map.put("errMsg", "职位id不能为空");
                return map;
            }
            position = positionService.queryById(positionId);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
            return map;
        }
        map.put("success", true);
        map.put("data", position);
        return map;
    }

    @ApiOperation(value = "查询status=1的列表数据")
    @PostMapping("/queryActivePositionList")
    @ResponseBody
    public Map<String, Object> queryActivePositionList() {
        Map<String, Object> map;
        try {
            map = positionService.queryActivePositionList();
        } catch (Exception e) {
            map = new HashMap<>(2);
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }
}
