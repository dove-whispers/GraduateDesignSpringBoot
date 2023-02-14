package com.dove.service;

import com.dove.dto.EmployeeDTO;

/**
 * 员工服务
 *
 * @author dove_whispers
 * @date 2023-02-10
 */
public interface EmployeeService {
    /**
     * 通过账户名密码联合查询
     * @param userName
     * @param password
     * @return
     */
    EmployeeDTO checkUserByUserNameAndPassword(String userName, String password);
}

