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

import java.util.List;


/**
 * 员工Dao
 *
 * @author dove_whispers
 * @date 2023-02-10
 */
public interface EmployeeDao extends BaseMapper<Employee> {
    /**
     * 通过ID查询单条数据
     *
     * @param emId 主键
     * @return 实例对象
     */
    Employee queryById(Integer emId);

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
     * 用户名称模糊查询id
     *
     * @param partName 用户名称
     * @return {@link Integer}
     */
    List<Integer> queryIdByPartName(String partName);

    /**
     * 查询部门人员id
     *
     * @param depId 部门id
     * @return {@link List}<{@link Integer}>
     */
    List<Integer> queryIdByDep(Integer depId);

    /**
     * 通过职位id找人
     *
     * @param nextDealPositionId 下一个交易位置id
     * @return {@link Integer}
     */
    Integer findEmByPositionId(Integer nextDealPositionId);

    /**
     * 通过部门和职位找人
     *
     * @param depId      部门id
     * @param positionId 职位id
     * @return {@link Integer}
     */
    Integer findEmByDepIdAndPositionId(Integer depId, Integer positionId);

    /**
     * 完整id查询
     *
     * @param emId em id
     * @return {@link EmployeeDTO}
     */
    EmployeeDTO completeQueryById(Integer emId);
}

