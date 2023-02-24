package com.dove.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dove.dto.EmployeeDTO;
import com.dove.dto.requestDTO.EmployeeListRequestDTO;
import com.dove.entity.Employee;
import org.apache.ibatis.annotations.Param;


/**
 * 员工Dao
 *
 * @author dove_whispers
 * @date 2023-02-10
 */
public interface EmployeeDao {
    /**
     * 通过账户名密码联合查询
     *
     * @param userName
     * @param password
     * @return
     */
    EmployeeDTO queryEmInfoByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);

    /**
     * 查询员工页面列表
     * 分页+筛选部门列表
     * 使用MybatisPlus分页
     *
     * @param page    页面
     * @param wrapper 包装器
     * @return {@link IPage}<{@link EmployeeDTO}>
     */
    IPage<EmployeeDTO> queryPageList(Page<EmployeeListRequestDTO> page, @Param(Constants.WRAPPER) QueryWrapper<EmployeeListRequestDTO> wrapper);

    /**
     * 通过id查询员工
     *
     * @param emId em id
     * @return {@link Employee}
     */
    Employee queryById(Integer emId);

    /**
     * 插入员工信息
     *
     * @param employee 员工
     */
    void insert(Employee employee);

    /**
     * 更新员工信息
     *
     * @param employee 员工
     */
    void update(Employee employee);
}

