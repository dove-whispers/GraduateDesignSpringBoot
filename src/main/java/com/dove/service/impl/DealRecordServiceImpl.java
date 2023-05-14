package com.dove.service.impl;

import com.dove.constants.Constants.*;
import com.dove.dao.DealRecordDao;
import com.dove.dao.EmployeeDao;
import com.dove.dao.ExpenseReportDao;
import com.dove.dao.PositionDao;
import com.dove.dto.DealRecordDTO;
import com.dove.dto.EmployeeDTO;
import com.dove.dto.requestDTO.AuditDealRecordRequestDTO;
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
import java.util.Date;

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
            Integer nextDealEm = expenseReport.getNextDealEm();
            Employee employee = employeeDao.queryById(dealRecord.getEmId());
            Position position = positionDao.queryById(employee.getPositionId());
            String positionName = position.getPositionName();
            String dealWay = dealRecord.getDealWay();
            String dealResult = dealRecord.getDealResult();
            Employee nextDealEmployee;
            Position nextDealEmployeePosition;
            String nextDealEmployeePositionName = null;
            if (0 != nextDealEm) {
                nextDealEmployee = employeeDao.queryById(nextDealEm);
                nextDealEmployeePosition = positionDao.queryById(nextDealEmployee.getPositionId());
                nextDealEmployeePositionName = nextDealEmployeePosition.getPositionName();
            }
            if (Way.ABORT.equals(dealWay)) {
                return Step.ABANDONED;
            } else if (Way.CREATE.equals(dealWay)) {
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
                    if (Result.REMITTANCE.equals(dealResult)) {
                        return Step.PASSED_BY_FINANCIAL_SUPERVISOR;
                    } else if (Result.REPULSE.equals(dealResult)) {
                        return Step.REPULSED_BY_FINANCIAL_SUPERVISOR;
                    } else {
                        return Step.TERMINATED_BY_FINANCIAL_SUPERVISOR;
                    }
                }
            } else {
                if (Name.DEPARTMENT_MANAGER.equals(nextDealEmployeePositionName)) {
                    return Step.CREATED;
                } else if (Name.GENERAL_MANAGER.equals(nextDealEmployeePositionName)) {
                    return Step.PASSED_BY_DEPARTMENT_MANAGER;
                } else {
                    return Step.PASSED_BY_GENERAL_MANAGER;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 查询指定报销单最新操作记录
     *
     * @param expensiveId 报销单id
     * @return {@link DealRecordDTO}
     */
    @Override
    public DealRecordDTO queryLatestDealRecord(Integer expensiveId) {
        return this.dealRecordDao.queryLatestDealRecord(expensiveId);
    }

    /**
     * 查询指定报销单最新操作记录
     *
     * @param expensiveId 报销单id
     * @return {@link DealRecord}
     */
    @Override
    public DealRecord queryExpensiveLatestDeal(Integer expensiveId) {
        return this.dealRecordDao.queryExpensiveLatestDeal(expensiveId);
    }

    /**
     * 添加审核记录
     *
     * @param requestDTO   请求dto
     * @param userInfo     用户信息
     * @param nextDealEmId 下一处理人id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addAuditRecord(AuditDealRecordRequestDTO requestDTO, EmployeeDTO userInfo, Integer nextDealEmId) {
        String positionName = userInfo.getPosition().getPositionName();
        String way = requestDTO.getWay();
        Integer expenseId = requestDTO.getExpenseId();
        String dealResult = null;
        String status = null;
        String pass = "通过";
        String repulse = "打回";
        String terminate = "终止";
        if (pass.equals(way)) {
            if (Name.FINANCIAL_SUPERVISOR.equals(positionName)) {
                dealResult = Result.REMITTANCE;
                status = Status.PAID;
            } else if (Name.GENERAL_MANAGER.equals(positionName)) {
                dealResult = Result.PASS;
                status = Status.TO_BE_FINANCE_REVIEWED;
            } else if (Name.DEPARTMENT_MANAGER.equals(positionName)) {
                dealResult = Result.PASS;
                status = Status.TO_BE_GM_REVIEWED;
            }
        } else if (repulse.equals(way)) {
            dealResult = Result.REPULSE;
            status = Status.TO_BE_MODIFIED;
        } else if (terminate.equals(way)) {
            dealResult = Result.TERMINATED;
            status = Status.TERMINATED;
        }
        try {
            DealRecord dealRecord = new DealRecord(null, expenseId, userInfo.getEmId(), new Date(), Way.AUDIT, dealResult, requestDTO.getComment());
            ExpenseReport expenseReport = expenseReportDao.queryById(expenseId);
            expenseReport.setNextDealEm(nextDealEmId);
            expenseReport.setStatus(status);
            this.expenseReportDao.update(expenseReport);
            this.dealRecordDao.insert(dealRecord);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
