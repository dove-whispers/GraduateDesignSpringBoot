package com.dove.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dove.dto.requestDTO.PositionListRequestDTO;
import com.dove.dto.responseDTO.PositionListResponseDTO;
import com.dove.entity.Position;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 职位表(Position)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-10 16:26:38
 */
public interface PositionDao {

    /**
     * 通过ID查询单条数据
     *
     * @param positionId 主键
     * @return 实例对象
     */
    Position queryById(Integer positionId);

    /**
     * 查询指定行数据
     *
     * @param position 查询条件
     * @param pageable 分页对象
     * @return 对象列表
     */
    List<Position> queryAllByLimit(Position position, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param position 查询条件
     * @return 总行数
     */
    long count(Position position);

    /**
     * 新增数据
     *
     * @param position 实例对象
     * @return 影响行数
     */
    int insert(Position position);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Position> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Position> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Position> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Position> entities);

    /**
     * 修改数据
     *
     * @param position 实例对象
     * @return 影响行数
     */
    int update(Position position);

    /**
     * 通过主键删除数据
     *
     * @param positionId 主键
     * @return 影响行数
     */
    int deleteById(Integer positionId);

    /**
     * 查询职位页面列表
     * 分页+筛选部门列表
     * 使用MybatisPlus分页
     *
     * @param page    页面
     * @param wrapper 包装器
     * @return {@link IPage}<{@link PositionListResponseDTO}>
     */
    IPage<PositionListResponseDTO> queryPageList(Page<PositionListRequestDTO> page, @Param(Constants.WRAPPER) QueryWrapper<PositionListRequestDTO> wrapper);

    /**
     * 将指定职位状态修改为0
     *
     * @param positionId 职位id
     */
    void updateFailureStatusById(Integer positionId);

    /**
     * 将指定职位状态修改为1
     *
     * @param positionId 职位id
     */
    void updateSuccessStatusById(Integer positionId);
}

