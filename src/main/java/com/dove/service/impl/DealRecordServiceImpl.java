package com.dove.service.impl;

import com.dove.entity.DealRecord;
import com.dove.dao.DealRecordDao;
import com.dove.service.DealRecordService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

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
     * @param dealRecord 筛选条件
     * @param pageRequest      分页对象
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
}
