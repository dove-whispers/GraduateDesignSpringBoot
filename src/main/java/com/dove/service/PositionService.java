package com.dove.service;

import com.dove.entity.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 职位表(Position)表服务接口
 *
 * @author makejava
 * @since 2023-02-10 16:26:38
 */
public interface PositionService {

    /**
     * 通过ID查询单条数据
     *
     * @param positionId 主键
     * @return 实例对象
     */
    Position queryById(Integer positionId);

    /**
     * 分页查询
     *
     * @param position 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<Position> queryByPage(Position position, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param position 实例对象
     * @return 实例对象
     */
    Position insert(Position position);

    /**
     * 修改数据
     *
     * @param position 实例对象
     * @return 实例对象
     */
    Position update(Position position);

    /**
     * 通过主键删除数据
     *
     * @param positionId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer positionId);

}
