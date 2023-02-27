package com.dove.dao;

import com.dove.entity.ExpenseReportDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 报销单细节表(ExpenseReportDetail)表数据库访问层
 *
 * @author dove_whispers
 * @date 2023-02-27
 */
public interface ExpenseReportDetailDao {

    /**
     * 通过ID查询单条数据
     *
     * @param expensiveDetailId 主键
     * @return 实例对象
     */
    ExpenseReportDetail queryById(Integer expensiveDetailId);

    /**
     * 查询指定行数据
     *
     * @param expenseReportDetail 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<ExpenseReportDetail> queryAllByLimit(ExpenseReportDetail expenseReportDetail, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param expenseReportDetail 查询条件
     * @return 总行数
     */
    long count(ExpenseReportDetail expenseReportDetail);

    /**
     * 新增数据
     *
     * @param expenseReportDetail 实例对象
     * @return 影响行数
     */
    int insert(ExpenseReportDetail expenseReportDetail);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ExpenseReportDetail> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ExpenseReportDetail> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ExpenseReportDetail> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ExpenseReportDetail> entities);

    /**
     * 修改数据
     *
     * @param expenseReportDetail 实例对象
     * @return 影响行数
     */
    int update(ExpenseReportDetail expenseReportDetail);

    /**
     * 通过主键删除数据
     *
     * @param expensiveDetailId 主键
     * @return 影响行数
     */
    int deleteById(Integer expensiveDetailId);

}

