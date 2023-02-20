package com.dove.service;

import com.dove.dto.requestDTO.DepartmentListRequestDTO;
import com.dove.dto.requestDTO.ToggleDepartmentRequestDTO;
import com.dove.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

/**
 * 部门表(Department)表服务接口
 *
 * @author makejava
 * @since 2023-02-10 16:26:38
 */
public interface DepartmentService {

    /**
     * 通过ID查询单条数据
     *
     * @param depId 主键
     * @return 实例对象
     */
    Department queryById(Integer depId);

    /**
     * 分页查询
     *
     * @param department  筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<Department> queryByPage(Department department, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param department 实例对象
     * @return 实例对象
     */
    Department insert(Department department);

    /**
     * 修改数据
     *
     * @param department 实例对象
     * @return 实例对象
     */
    Department update(Department department);

    /**
     * 通过主键删除数据
     *
     * @param depId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer depId);

    /**
     * 查询部门页面列表
     *
     * @param departmentListRequestDTO 部门列表请求dto
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> queryPageList(DepartmentListRequestDTO departmentListRequestDTO);

    /**
     * 单个部门切换状态
     *
     * @param toggleDepartmentRequestDTO 请求dto
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> toggleStatus(ToggleDepartmentRequestDTO toggleDepartmentRequestDTO);
}
