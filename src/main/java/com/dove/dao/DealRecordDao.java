package com.dove.dao;

import com.dove.dto.DealRecordDTO;
import com.dove.entity.DealRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;


/**
 * 操作记录表(DealRecord)表数据库访问层
 *
 * @author dove_whispers
 * @date 2023-02-27
 */
public interface DealRecordDao {

    /**
     * 通过ID查询单条数据
     *
     * @param recordId 主键
     * @return 实例对象
     */
    DealRecord queryById(Integer recordId);

    /**
     * 查询指定行数据
     *
     * @param dealRecord 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<DealRecord> queryAllByLimit(DealRecord dealRecord, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param dealRecord 查询条件
     * @return 总行数
     */
    long count(DealRecord dealRecord);

    /**
     * 新增数据
     *
     * @param dealRecord 实例对象
     * @return 影响行数
     */
    int insert(DealRecord dealRecord);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<DealRecord> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<DealRecord> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<DealRecord> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<DealRecord> entities);

    /**
     * 修改数据
     *
     * @param dealRecord 实例对象
     * @return 影响行数
     */
    int update(DealRecord dealRecord);

    /**
     * 通过主键删除数据
     *
     * @param recordId 主键
     * @return 影响行数
     */
    int deleteById(Integer recordId);

    /**
     * 查询报销单最新操作记录
     *
     * @param expensiveId 报销单id
     * @return {@link DealRecord}
     */
    DealRecord queryExpensiveLatestDeal(Integer expensiveId);

    /**
     * 查询最新操作记录
     *
     * @param expensiveId 报销单id
     * @return {@link DealRecordDTO}
     */
    DealRecordDTO queryLatestDealRecord(Integer expensiveId);

    /**
     * 通过报销单id和处理人职位找到处理人id
     *
     * @param expensiveId  报销单id
     * @param positionName 职位名称
     * @return {@link Integer}
     */
    Integer findEmByExpenseIdAndPositionName(Integer expensiveId, String positionName);
}

