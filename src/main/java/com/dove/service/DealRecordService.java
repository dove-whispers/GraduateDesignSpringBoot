package com.dove.service;

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
     * @param dealRecord 筛选条件
     * @param pageRequest      分页对象
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

}
