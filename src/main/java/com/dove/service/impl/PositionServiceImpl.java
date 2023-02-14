package com.dove.service.impl;

import com.dove.entity.Position;
import com.dove.dao.PositionDao;
import com.dove.service.PositionService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 职位表(Position)表服务实现类
 *
 * @author makejava
 * @since 2023-02-10 16:26:38
 */
@Service("positionService")
public class PositionServiceImpl implements PositionService {
    @Resource
    private PositionDao positionDao;

    /**
     * 通过ID查询单条数据
     *
     * @param positionId 主键
     * @return 实例对象
     */
    @Override
    public Position queryById(Integer positionId) {
        return this.positionDao.queryById(positionId);
    }

    /**
     * 分页查询
     *
     * @param position 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<Position> queryByPage(Position position, PageRequest pageRequest) {
        long total = this.positionDao.count(position);
        return new PageImpl<>(this.positionDao.queryAllByLimit(position, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param position 实例对象
     * @return 实例对象
     */
    @Override
    public Position insert(Position position) {
        this.positionDao.insert(position);
        return position;
    }

    /**
     * 修改数据
     *
     * @param position 实例对象
     * @return 实例对象
     */
    @Override
    public Position update(Position position) {
        this.positionDao.update(position);
        return this.queryById(position.getPositionId());
    }

    /**
     * 通过主键删除数据
     *
     * @param positionId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer positionId) {
        return this.positionDao.deleteById(positionId) > 0;
    }
}
