package com.dove.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dove.constants.Constants;
import com.dove.dao.EmployeeDao;
import com.dove.dao.ExpenseReportDao;
import com.dove.dto.ExpenseReportDTO;
import com.dove.dto.requestDTO.ExpenseReportMainListRequestDTO;
import com.dove.entity.Department;
import com.dove.entity.ExpenseReport;
import com.dove.entity.Position;
import com.dove.service.ExpenseReportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 报销单表(ExpenseReport)表服务实现类
 *
 * @author dove_whispers
 * @date 2023-03-04
 */
@Service("expenseReportService")
public class ExpenseReportServiceImpl implements ExpenseReportService {
    @Resource
    private ExpenseReportDao expenseReportDao;
    @Resource
    private EmployeeDao employeeDao;

    /**
     * 通过ID查询单条数据
     *
     * @param expenseId 主键
     * @return 实例对象
     */
    @Override
    public ExpenseReport queryById(Integer expenseId) {
        return this.expenseReportDao.queryById(expenseId);
    }

    /**
     * 分页查询
     *
     * @param expenseReport 筛选条件
     * @param pageRequest   分页对象
     * @return 查询结果
     */
    @Override
    public Page<ExpenseReport> queryByPage(ExpenseReport expenseReport, PageRequest pageRequest) {
        long total = this.expenseReportDao.count(expenseReport);
        return new PageImpl<>(this.expenseReportDao.queryAllByLimit(expenseReport, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param expenseReport 实例对象
     * @return 实例对象
     */
    @Override
    public ExpenseReport insert(ExpenseReport expenseReport) {
        this.expenseReportDao.insert(expenseReport);
        return expenseReport;
    }

    /**
     * 修改数据
     *
     * @param expenseReport 实例对象
     * @return 实例对象
     */
    @Override
    public ExpenseReport update(ExpenseReport expenseReport) {
        this.expenseReportDao.update(expenseReport);
        return this.queryById(expenseReport.getExpenseId());
    }

    /**
     * 通过主键删除数据
     *
     * @param expenseId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer expenseId) {
        return this.expenseReportDao.deleteById(expenseId) > 0;
    }

    /**
     * 主页查询报销单列表
     *
     * @param requestDTO 请求dto
     * @param emId       登录人id
     * @param department 登录人部门
     * @param position   登录人位置
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    public Map<String, Object> queryMainPageList(ExpenseReportMainListRequestDTO requestDTO, Integer emId, Department department, Position position) {
        Map<String, Object> map = new HashMap<>(2);
        String positionName = position.getPositionName();
        try {
            QueryWrapper<ExpenseReportMainListRequestDTO> queryWrapper = new QueryWrapper<>();
            queryWrapper.like(StrUtil.isNotEmpty(requestDTO.getCause()), "cause", requestDTO.getCause())
                    .inSql(StrUtil.isNotEmpty(requestDTO.getName()), "em_id", "SELECT em_id FROM employee WHERE name LIKE '%" + requestDTO.getName() + "%'")
                    .eq(positionName.equals(Constants.Name.STAFF), "em_id", emId)
                    .inSql(positionName.equals(Constants.Name.DEPARTMENT_MANAGER), "em_id", "SELECT em_id FROM employee WHERE dep_id = " + department.getDepId())
                    .notIn(Objects.nonNull(requestDTO.getStatus()), "status", Constants.Status.PAID, Constants.Status.TERMINATED);
            com.baomidou.mybatisplus.extension.plugins.pagination.Page<ExpenseReportMainListRequestDTO> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(requestDTO.getCurrent(), requestDTO.getSize());
            IPage<ExpenseReportDTO> reports = expenseReportDao.queryMainPageList(page, queryWrapper);
            map.put("success", true);
            map.put("data", reports);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }
}
