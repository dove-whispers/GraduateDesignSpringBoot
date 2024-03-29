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
     *
     * @param username 账号
     * @param password 密码
     * @return {@link EmployeeDTO}
     */
    EmployeeDTO checkUserByUserNameAndPassword(String username, String password);

    /**
     * 检查用户用户名和加密密码
     *
     * @param username 用户名
     * @param password 加密密码
     * @return {@link EmployeeDTO}
     */
    EmployeeDTO checkUserByUserNameAndMdPassword(String username, String password);

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

    /**
     * 查询下一个待处理人的Id
     *
     * @param emId  em id
     * @param depId 部门id
     * @return {@link Integer}
     */
    Integer queryNextDealEmId(Integer emId, Integer depId);

    /**
     * 查询前一个处理人id
     *
     * @param userInfo  用户信息
     * @param expenseId 报销单id
     * @return {@link Integer}
     */
    Integer queryFormerDealEmId(EmployeeDTO userInfo, Integer expenseId);
}

