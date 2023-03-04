package com.dove.service.impl;

import com.dove.entity.ExpenseReport;
import com.dove.dao.ExpenseReportDao;
import com.dove.service.ExpenseReportService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

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
     * @param pageRequest      分页对象
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
}
