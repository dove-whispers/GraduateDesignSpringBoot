package com.dove.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dove.dao.DepartmentDao;
import com.dove.dto.requestDTO.DepartmentListRequestDTO;
import com.dove.dto.requestDTO.ToggleDepartmentRequestDTO;
import com.dove.dto.responseDTO.ActiveDepartmentListResponseDTO;
import com.dove.dto.responseDTO.DepartmentListResponseDTO;
import com.dove.entity.Department;
import com.dove.service.DepartmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 部门表(Department)表服务实现类
 *
 * @author makejava
 * @since 2023-02-10 16:26:38
 */
@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {
    @Resource
    private DepartmentDao departmentDao;

    /**
     * 通过ID查询单条数据
     *
     * @param depId 主键
     * @return 实例对象
     */
    @Override
    public Department queryById(Integer depId) {
        return this.departmentDao.queryById(depId);
    }

    /**
     * 分页查询
     *
     * @param department  筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<Department> queryByPage(Department department, PageRequest pageRequest) {
        long total = this.departmentDao.count(department);
        return new PageImpl<>(this.departmentDao.queryAllByLimit(department, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param department 实例对象
     * @return 实例对象
     */
    @Override
    public Department insert(Department department) {
        this.departmentDao.insert(department);
        return department;
    }

    /**
     * 修改数据
     *
     * @param department 实例对象
     * @return 实例对象
     */
    @Override
    public Department update(Department department) {
        this.departmentDao.update(department);
        return this.queryById(department.getDepId());
    }

    /**
     * 通过主键删除数据
     *
     * @param depId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer depId) {
        return this.departmentDao.deleteById(depId) > 0;
    }

    @Override
    public Map<String, Object> queryPageList(DepartmentListRequestDTO requestDTO) {
        Map<String, Object> map = new HashMap<>(2);
        try {
            QueryWrapper<DepartmentListRequestDTO> wrapper = new QueryWrapper<>();
            wrapper.like(StrUtil.isNotEmpty(requestDTO.getName()), "name", requestDTO.getName())
                    .like(StrUtil.isNotEmpty(requestDTO.getAddress()), "address", requestDTO.getAddress())
                    .eq(Objects.nonNull(requestDTO.getStatus()), "status", requestDTO.getStatus());
            com.baomidou.mybatisplus.extension.plugins.pagination.Page<DepartmentListRequestDTO> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(requestDTO.getCurrent(), requestDTO.getSize());
            IPage<DepartmentListResponseDTO> departments = departmentDao.queryPageList(page, wrapper);
            map.put("success", true);
            map.put("data", departments);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }

    /**
     * 单个部门切换状态
     *
     * @param requestDTO 请求dto
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> toggleStatus(ToggleDepartmentRequestDTO requestDTO) {
        Map<String, Object> map = new HashMap<>(2);
        Integer status = requestDTO.getStatus();
        Integer depId = requestDTO.getDepId();
        try {
            if (1 == status) {
                departmentDao.updateFailureStatusById(depId);
            } else {
                departmentDao.updateSuccessStatusById(depId);
            }
            map.put("success", true);
        } catch (Exception e) {
            //TODO:应该抛出自定义异常
            e.printStackTrace();
            throw e;
        }
        return map;
    }

    /**
     * 查询活动部门列表
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    public Map<String, Object> queryActiveDepartmentList() {
        Map<String, Object> map = new HashMap<>(2);
        try {
            List<ActiveDepartmentListResponseDTO> activeDepartments = departmentDao.queryActiveDepartmentList();
            map.put("success", true);
            map.put("data", activeDepartments);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }
}
