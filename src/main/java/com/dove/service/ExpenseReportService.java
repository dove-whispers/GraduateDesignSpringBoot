package com.dove.service;

import com.dove.dto.EmployeeDTO;
import com.dove.dto.requestDTO.ExpenseReportMainListRequestDTO;
import com.dove.dto.requestDTO.ExpenseReportRequestDTO;
import com.dove.entity.Department;
import com.dove.entity.ExpenseReport;
import com.dove.entity.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

/**
 * 报销单表(ExpenseReport)表服务接口
 *
 * @author dove_whispers
 * @date 2023-03-04
 */
public interface ExpenseReportService {

    /**
     * 通过ID查询单条数据
     *
     * @param expenseId 主键
     * @return 实例对象
     */
    ExpenseReport queryById(Integer expenseId);

    /**
     * 分页查询
     *
     * @param expenseReport 筛选条件
     * @param pageRequest   分页对象
     * @return 查询结果
     */
    Page<ExpenseReport> queryByPage(ExpenseReport expenseReport, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param expenseReport 实例对象
     * @return 实例对象
     */
    ExpenseReport insert(ExpenseReport expenseReport);

    /**
     * 修改数据
     *
     * @param expenseReport 实例对象
     * @return 实例对象
     */
    ExpenseReport update(ExpenseReport expenseReport);

    /**
     * 通过主键删除数据
     *
     * @param expenseId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer expenseId);

    /**
     * 主页查询报销单列表
     *
     * @param requestDTO 请求dto
     * @param emId       登录人id
     * @param department 登录人部门
     * @param position   登录人位置
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> queryMainPageList(ExpenseReportMainListRequestDTO requestDTO, Integer emId, Department department, Position position);

    /**
     * 添加报销单
     *
     * @param userInfo   用户信息
     * @param requestDTO 请求dto
     */
    void addExpenseReport(EmployeeDTO userInfo, ExpenseReportRequestDTO requestDTO);
}
