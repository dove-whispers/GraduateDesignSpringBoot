package com.dove.service;

import com.dove.entity.ExpenseReportDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 报销单细节表(ExpenseReportDetail)表服务接口
 *
 * @author dove_whispers
 * @date 2023-02-27
 */
public interface ExpenseReportDetailService {

    /**
     * 通过ID查询单条数据
     *
     * @param expensiveDetailId 主键
     * @return 实例对象
     */
    ExpenseReportDetail queryById(Integer expensiveDetailId);

    /**
     * 分页查询
     *
     * @param expenseReportDetail 筛选条件
     * @param pageRequest         分页对象
     * @return 查询结果
     */
    Page<ExpenseReportDetail> queryByPage(ExpenseReportDetail expenseReportDetail, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param expenseReportDetail 实例对象
     * @return 实例对象
     */
    ExpenseReportDetail insert(ExpenseReportDetail expenseReportDetail);

    /**
     * 修改数据
     *
     * @param expenseReportDetail 实例对象
     * @return 实例对象
     */
    ExpenseReportDetail update(ExpenseReportDetail expenseReportDetail);

    /**
     * 通过主键删除数据
     *
     * @param expensiveDetailId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer expensiveDetailId);

}
