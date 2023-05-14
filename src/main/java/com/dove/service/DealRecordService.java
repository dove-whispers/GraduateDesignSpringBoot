package com.dove.service;

import com.dove.dto.DealRecordDTO;
import com.dove.dto.EmployeeDTO;
import com.dove.dto.requestDTO.AuditDealRecordRequestDTO;
import com.dove.entity.DealRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 操作记录表(DealRecord)表服务接口
 *
 * @author dove_whispers
 * @date 2023-02-27
 */
public interface DealRecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param recordId 主键
     * @return 实例对象
     */
    DealRecord queryById(Integer recordId);

    /**
     * 分页查询
     *
     * @param dealRecord  筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<DealRecord> queryByPage(DealRecord dealRecord, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param dealRecord 实例对象
     * @return 实例对象
     */
    DealRecord insert(DealRecord dealRecord);

    /**
     * 修改数据
     *
     * @param dealRecord 实例对象
     * @return 实例对象
     */
    DealRecord update(DealRecord dealRecord);

    /**
     * 通过主键删除数据
     *
     * @param recordId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer recordId);

    /**
     * 查询报销单报销进度
     *
     * @param expenseId 费用id
     * @return {@link Integer}
     */
    Integer queryExpenseReportStep(Integer expenseId);

    /**
     * 查询指定报销单最新操作记录
     *
     * @param expensiveId 报销单id
     * @return {@link DealRecordDTO}
     */
    DealRecordDTO queryLatestDealRecord(Integer expensiveId);

    /**
     * 查询指定报销单最新操作记录
     *
     * @param expensiveId 报销单id
     * @return {@link DealRecord}
     */
    DealRecord queryExpensiveLatestDeal(Integer expensiveId);

    /**
     * 添加审核记录
     *
     * @param requestDTO   请求dto
     * @param userInfo     用户信息
     * @param nextDealEmId 下一处理人id
     */
    void addAuditRecord(AuditDealRecordRequestDTO requestDTO, EmployeeDTO userInfo, Integer nextDealEmId);
}
