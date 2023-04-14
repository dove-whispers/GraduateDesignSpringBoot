package com.dove.service.impl;

import com.dove.dao.ExpenseReportDetailDao;
import com.dove.dto.requestDTO.ExpenseReportCheckRequestDTO;
import com.dove.entity.ExpenseReportDetail;
import com.dove.service.ExpenseReportDetailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 报销单细节表(ExpenseReportDetail)表服务实现类
 *
 * @author dove_whispers
 * @date 2023-03-02
 */
@Service("expenseReportDetailService")
public class ExpenseReportDetailServiceImpl implements ExpenseReportDetailService {
    @Resource
    private ExpenseReportDetailDao expenseReportDetailDao;

    /**
     * 通过ID查询单条数据
     *
     * @param expensiveDetailId 主键
     * @return 实例对象
     */
    @Override
    public ExpenseReportDetail queryById(Integer expensiveDetailId) {
        return this.expenseReportDetailDao.queryById(expensiveDetailId);
    }

    /**
     * 分页查询
     *
     * @param expenseReportDetail 筛选条件
     * @param pageRequest         分页对象
     * @return 查询结果
     */
    @Override
    public Page<ExpenseReportDetail> queryByPage(ExpenseReportDetail expenseReportDetail, PageRequest pageRequest) {
        long total = this.expenseReportDetailDao.count(expenseReportDetail);
        return new PageImpl<>(this.expenseReportDetailDao.queryAllByLimit(expenseReportDetail, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param expenseReportDetail 实例对象
     * @return 实例对象
     */
    @Override
    public ExpenseReportDetail insert(ExpenseReportDetail expenseReportDetail) {
        this.expenseReportDetailDao.insert(expenseReportDetail);
        return expenseReportDetail;
    }

    /**
     * 修改数据
     *
     * @param expenseReportDetail 实例对象
     * @return 实例对象
     */
    @Override
    public ExpenseReportDetail update(ExpenseReportDetail expenseReportDetail) {
        this.expenseReportDetailDao.update(expenseReportDetail);
        return this.queryById(expenseReportDetail.getExpensiveDetailId());
    }

    /**
     * 通过主键删除数据
     *
     * @param expensiveDetailId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer expensiveDetailId) {
        return this.expenseReportDetailDao.deleteById(expensiveDetailId) > 0;
    }

    /**
     * 报销单查重
     *
     * @param requestDTO 请求dto
     * @return boolean
     */
    @Override
    public boolean checkExist(ExpenseReportCheckRequestDTO requestDTO) {
        return expenseReportDetailDao.queryExistByCodeAndNum(requestDTO) != null;
    }
}
