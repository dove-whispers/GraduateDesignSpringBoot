package com.dove.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dove.constants.Constants.Name;
import com.dove.dao.EmployeeDao;
import com.dove.dao.PositionDao;
import com.dove.dto.EmployeeDTO;
import com.dove.dto.requestDTO.EmployeeListRequestDTO;
import com.dove.dto.requestDTO.ToggleEmployeeRequestDTO;
import com.dove.entity.Employee;
import com.dove.entity.Position;
import com.dove.service.EmployeeService;
import com.dove.util.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 员工服务impl
 *
 * @author dove_whispers
 * @date 2023-02-10
 */
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {
    @Resource
    private EmployeeDao employeeDao;
    @Resource
    private PositionDao positionDao;

    /**
     * 通过账户名密码联合查询
     *
     * @param username 账号
     * @param password 密码
     * @return {@link EmployeeDTO}
     */
    @Override
    public EmployeeDTO checkUserByUserNameAndPassword(String username, String password) {
        return employeeDao.queryEmInfoByUserNameAndPassword(username, MD5Util.getMD5(password));
    }

    /**
     * 检查用户用户名和加密密码
     *
     * @param username 用户名
     * @param password 加密密码
     * @return {@link EmployeeDTO}
     */
    @Override
    public EmployeeDTO checkUserByUserNameAndMdPassword(String username, String password) {
        return employeeDao.queryEmInfoByUserNameAndMdPassword(username, password);
    }

    /**
     * 查询员工页面列表
     *
     * @param requestDTO 雇员列表请求dto
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    public Map<String, Object> queryPageList(EmployeeListRequestDTO requestDTO) {
        Map<String, Object> map = new HashMap<>(2);
        try {
            QueryWrapper<EmployeeListRequestDTO> wrapper = new QueryWrapper<>();
            wrapper.like(StrUtil.isNotEmpty(requestDTO.getName()), "name", requestDTO.getName())
                    .eq(Objects.nonNull(requestDTO.getDepId()), "dep_id", requestDTO.getDepId())
                    .eq(Objects.nonNull(requestDTO.getPositionId()), "position_id", requestDTO.getPositionId())
                    .eq(Objects.nonNull(requestDTO.getStatus()), "status", requestDTO.getStatus());
            com.baomidou.mybatisplus.extension.plugins.pagination.Page<EmployeeListRequestDTO> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(requestDTO.getCurrent(), requestDTO.getSize());
            IPage<EmployeeDTO> employees = employeeDao.queryPageList(page, wrapper);
            map.put("success", true);
            map.put("data", employees);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }

    /**
     * 通过id查询员工
     *
     * @param emId em id
     * @return {@link Employee}
     */
    @Override
    public Employee queryById(Integer emId) {
        return this.employeeDao.selectById(emId);
    }

    /**
     * 插入员工
     *
     * @param employee 员工
     */
    @Override
    public Employee insert(Employee employee) {
        //设置默认密码
        employee.setPassword(MD5Util.getMD5("123456"));
        this.employeeDao.insert(employee);
        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        this.employeeDao.updateById(employee);
        return this.queryById(employee.getEmId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> toggleStatus(ToggleEmployeeRequestDTO requestDTO) {
        Map<String, Object> map = new HashMap<>(2);
        Integer status = requestDTO.getStatus();
        Integer emId = requestDTO.getEmId();
        try {
            if (1 == status) {
                employeeDao.updateFailureStatusById(emId);
            } else {
                employeeDao.updateSuccessStatusById(emId);
            }
            map.put("success", true);
        } catch (Exception e) {
            //TODO:应该抛出自定义异常
            e.printStackTrace();
            throw e;
        }
        return map;
    }

    /**
     * 查询下一个待处理人的Id
     *
     * @param emId  em id
     * @param depId 部门id
     * @return {@link Integer}
     */
    @Override
    public Integer queryNextDealEmId(Integer emId, Integer depId) {
        Employee employee = employeeDao.selectById(emId);
        Integer positionId = employee.getPositionId();
        Position position = positionDao.queryById(positionId);
        String positionName = position.getPositionName();
        String nextPositionName;
        if (Name.STAFF.equals(positionName)) {
            nextPositionName = Name.DEPARTMENT_MANAGER;
        } else if (Name.DEPARTMENT_MANAGER.equals(positionName)) {
            nextPositionName = Name.GENERAL_MANAGER;
        } else if (Name.GENERAL_MANAGER.equals(positionName)) {
            nextPositionName = Name.FINANCIAL_SUPERVISOR;
        } else {
            nextPositionName = Name.TENTATIVE;
        }
        if (Name.TENTATIVE.equals(nextPositionName)) {
            return 0;
        }
        Integer nextDealPositionId = positionDao.findPositionIdByPositionName(nextPositionName);
        if (Name.DEPARTMENT_MANAGER.equals(nextPositionName)) {
            return employeeDao.findEmByDepIdAndPositionId(depId, nextDealPositionId);
        }
        return employeeDao.findEmByPositionId(nextDealPositionId);
    }
}

