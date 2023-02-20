package com.dove.service;

import com.dove.dto.EmployeeDTO;
import com.dove.dto.requestDTO.EmployeeListRequestDTO;

import java.util.Map;

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

    /**
     * 查询员工页面列表
     *
     * @param employeeListRequestDTO 雇员列表请求dto
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> queryPageList(EmployeeListRequestDTO employeeListRequestDTO);
}

