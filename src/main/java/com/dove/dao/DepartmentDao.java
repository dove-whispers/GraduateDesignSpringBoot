package com.dove.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dove.dto.requestDTO.DepartmentListRequestDTO;
import com.dove.dto.responseDTO.ActiveDepartmentListResponseDTO;
import com.dove.dto.responseDTO.DepartmentListResponseDTO;
import com.dove.entity.Department;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 部门表(Department)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-10 16:26:38
 */
public interface DepartmentDao {

    /**
     * 通过ID查询单条数据
     *
     * @param depId 主键
     * @return 实例对象
     */
    Department queryById(Integer depId);

    /**
     * 查询指定行数据
     *
     * @param department 查询条件
     * @param pageable   分页对象
     * @return 对象列表
     */
    List<Department> queryAllByLimit(Department department, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param department 查询条件
     * @return 总行数
     */
    long count(Department department);

    /**
     * 新增数据
     *
     * @param department 实例对象
     * @return 影响行数
     */
    int insert(Department department);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Department> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Department> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Department> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Department> entities);

    /**
     * 修改数据
     *
     * @param department 实例对象
     * @return 影响行数
     */
    int update(Department department);

    /**
     * 通过主键删除数据
     *
     * @param depId 主键
     * @return 影响行数
     */
    int deleteById(Integer depId);

    /**
     * 查询部门页面列表
     * 分页+筛选部门列表
     * 使用MybatisPlus分页
     *
     * @param page         请求dto
     * @param queryWrapper 请求条件
     * @return {@link IPage}<{@link DepartmentListResponseDTO}>
     */
    IPage<DepartmentListResponseDTO> queryPageList(Page<DepartmentListRequestDTO> page, @Param(Constants.WRAPPER) Wrapper<DepartmentListRequestDTO> queryWrapper);

    /**
     * 将指定部门状态修改为0
     *
     * @param depId 部门id
     */
    void updateFailureStatusById(Integer depId);

    /**
     * 将指定部门状态修改为1
     *
     * @param depId 部门id
     */
    void updateSuccessStatusById(Integer depId);

    /**
     * 查询活动部门列表
     *
     * @return {@link List}<{@link ActiveDepartmentListResponseDTO}>
     */
    List<ActiveDepartmentListResponseDTO> queryActiveDepartmentList();
}

