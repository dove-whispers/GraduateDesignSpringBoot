package com.dove.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dove.dao.EmployeeDao;
import com.dove.dto.EmployeeDTO;
import com.dove.dto.requestDTO.EmployeeListRequestDTO;
import com.dove.entity.Employee;
import com.dove.service.EmployeeService;
import org.springframework.stereotype.Service;

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

    /**
     * 通过账户名密码联合查询
     *
     * @param userName
     * @param password
     * @return
     */
    @Override
    public EmployeeDTO checkUserByUserNameAndPassword(String userName, String password) {
        return employeeDao.queryEmInfoByUserNameAndPassword(userName, password);
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
        return this.employeeDao.queryById(emId);
    }

    /**
     * 插入员工
     *
     * @param employee 员工
     */
    @Override
    public Employee insert(Employee employee) {
        this.employeeDao.insert(employee);
        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        this.employeeDao.update(employee);
        return this.queryById(employee.getEmId());
    }
}

