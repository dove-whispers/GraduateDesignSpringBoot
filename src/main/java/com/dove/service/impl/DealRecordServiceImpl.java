package com.dove.service.impl;

import com.dove.constants.Constants.Name;
import com.dove.constants.Constants.Result;
import com.dove.constants.Constants.Step;
import com.dove.constants.Constants.Way;
import com.dove.dao.DealRecordDao;
import com.dove.dao.EmployeeDao;
import com.dove.dao.ExpenseReportDao;
import com.dove.dao.PositionDao;
import com.dove.dto.DealRecordDTO;
import com.dove.entity.DealRecord;
import com.dove.entity.Employee;
import com.dove.entity.ExpenseReport;
import com.dove.entity.Position;
import com.dove.service.DealRecordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 操作记录表(DealRecord)表服务实现类
 *
 * @author dove_whispers
 * @date 2023-02-27
 */
@Service("dealRecordService")
public class DealRecordServiceImpl implements DealRecordService {
    @Resource
    private DealRecordDao dealRecordDao;
    @Resource
    private ExpenseReportDao expenseReportDao;
    @Resource
    private EmployeeDao employeeDao;
    @Resource
    private PositionDao positionDao;

    /**
     * 通过ID查询单条数据
     *
     * @param recordId 主键
     * @return 实例对象
     */
    @Override
    public DealRecord queryById(Integer recordId) {
        return this.dealRecordDao.queryById(recordId);
    }

    /**
     * 分页查询
     *
     * @param dealRecord  筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<DealRecord> queryByPage(DealRecord dealRecord, PageRequest pageRequest) {
        long total = this.dealRecordDao.count(dealRecord);
        return new PageImpl<>(this.dealRecordDao.queryAllByLimit(dealRecord, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param dealRecord 实例对象
     * @return 实例对象
     */
    @Override
    public DealRecord insert(DealRecord dealRecord) {
        this.dealRecordDao.insert(dealRecord);
        return dealRecord;
    }

    /**
     * 修改数据
     *
     * @param dealRecord 实例对象
     * @return 实例对象
     */
    @Override
    public DealRecord update(DealRecord dealRecord) {
        this.dealRecordDao.update(dealRecord);
        return this.queryById(dealRecord.getRecordId());
    }

    /**
     * 通过主键删除数据
     *
     * @param recordId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer recordId) {
        return this.dealRecordDao.deleteById(recordId) > 0;
    }

    /**
     * 查询报销单报销进度
     *
     * @param expenseId 费用id
     * @return {@link Integer}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer queryExpenseReportStep(Integer expenseId) {
        try {
            DealRecord dealRecord = dealRecordDao.queryExpensiveLatestDeal(expenseId);
            ExpenseReport expenseReport = expenseReportDao.queryById(expenseId);
            Employee nextDealEmployee = employeeDao.queryById(expenseReport.getNextDealEm());
            Position nextDealEmployeePosition = positionDao.queryById(nextDealEmployee.getPositionId());
            String nextDealEmployeePositionName = nextDealEmployeePosition.getPositionName();
            Employee employee = employeeDao.queryById(dealRecord.getEmId());
            Position position = positionDao.queryById(employee.getPositionId());
            String positionName = position.getPositionName();
            String dealWay = dealRecord.getDealWay();
            String dealResult = dealRecord.getDealResult();
            if (Way.CREATE.equals(dealWay)) {
                return Step.CREATED;
            } else if (Way.AUDIT.equals(dealWay)) {
                if (Name.DEPARTMENT_MANAGER.equals(positionName)) {
                    if (Result.PASS.equals(dealResult)) {
                        return Step.PASSED_BY_DEPARTMENT_MANAGER;
                    } else if (Result.REPULSE.equals(dealResult)) {
                        return Step.REPULSED_BY_DEPARTMENT_MANAGER;
                    } else {
                        return Step.TERMINATED_BY_DEPARTMENT_MANAGER;
                    }
                } else if (Name.GENERAL_MANAGER.equals(positionName)) {
                    if (Result.PASS.equals(dealResult)) {
                        return Step.PASSED_BY_GENERAL_MANAGER;
                    } else if (Result.REPULSE.equals(dealResult)) {
                        return Step.REPULSED_BY_GENERAL_MANAGER;
                    } else {
                        return Step.TERMINATED_BY_GENERAL_MANAGER;
                    }
                } else {
                    if (Result.PASS.equals(dealResult)) {
                        return Step.PASSED_BY_FINANCIAL_SUPERVISOR;
                    } else if (Result.REPULSE.equals(dealResult)) {
                        return Step.REPULSED_BY_FINANCIAL_SUPERVISOR;
                    } else {
                        return Step.TERMINATED_BY_FINANCIAL_SUPERVISOR;
                    }
                }
            } else {
                if (Name.DEPARTMENT_MANAGER.equals(nextDealEmployeePositionName)){
                    return Step.CREATED;
                }else if (Name.GENERAL_MANAGER.equals(nextDealEmployeePositionName)){
                    return Step.PASSED_BY_DEPARTMENT_MANAGER;
                }else {
                    return Step.PASSED_BY_GENERAL_MANAGER;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 查询最新操作记录
     *
     * @param expensiveId 报销单id
     * @return {@link DealRecordDTO}
     */
    @Override
    public DealRecordDTO queryLatestDealRecord(Integer expensiveId) {
        return this.dealRecordDao.queryLatestDealRecord(expensiveId);
    }
}
