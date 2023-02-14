package com.dove.service.impl;

import com.dove.dao.EmployeeDao;
import com.dove.dto.EmployeeDTO;
import com.dove.service.EmployeeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}

