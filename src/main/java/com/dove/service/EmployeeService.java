package com.dove.service;

import com.dove.dto.EmployeeDTO;
import com.dove.dto.requestDTO.EmployeeListRequestDTO;
import com.dove.dto.requestDTO.ToggleEmployeeRequestDTO;
import com.dove.entity.Department;
import com.dove.entity.Employee;

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

    /**
     * 通过id查询员工
     *
     * @param emId em id
     * @return {@link Employee}
     */
    Employee queryById(Integer emId);

    /**
     * 插入员工
     *
     * @param employee 员工
     * @return {@link Employee}
     */
    Employee insert(Employee employee);

    /**
     * 更新员工信息
     *
     * @param employee 员工
     * @return {@link Employee}
     */
    Employee update(Employee employee);

    /**
     * 切换员工状态
     *
     * @param requestDTO 请求dto
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> toggleStatus(ToggleEmployeeRequestDTO requestDTO);
}

