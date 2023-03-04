package com.dove.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface EmployeeDao extends BaseMapper<Employee> {
    /**
     * 通过账户名密码联合查询
     *
     * @param userName 用户名(手机号)
     * @param password 密码
     * @return {@link EmployeeDTO}
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
     * 更新失败状态id
     *
     * @param emId em id
     */
    void updateFailureStatusById(Integer emId);

    /**
     * 状态更新成功id
     *
     * @param emId em id
     */
    void updateSuccessStatusById(Integer emId);

    /**
     * 通过部门和职位查询
     *
     * @param depId        部门id
     * @param depManagerId 部门经理id
     * @return {@link Integer}
     */
    Integer queryNextDealEmIdByDepAndPosition(Integer depId, Integer depManagerId);
}

