package com.dove.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dove.dao.PositionDao;
import com.dove.dto.requestDTO.PositionListRequestDTO;
import com.dove.dto.requestDTO.TogglePositionRequestDTO;
import com.dove.dto.responseDTO.PositionListResponseDTO;
import com.dove.entity.Position;
import com.dove.service.PositionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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
     * @param position    筛选条件
     * @param pageRequest 分页对象
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

    /**
     * 查询职位页面列表
     *
     * @param requestDTO 职位列表请求dto
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    public Map<String, Object> queryPageList(PositionListRequestDTO requestDTO) {
        Map<String, Object> map = new HashMap<>(2);
        try {
            QueryWrapper<PositionListRequestDTO> wrapper = new QueryWrapper<>();
            if (null != requestDTO.getPositionName()) {
                wrapper.like("position_name", requestDTO.getPositionName());
            }
            if (null != requestDTO.getStatus()) {
                wrapper.eq("status", requestDTO.getStatus());
            }
            com.baomidou.mybatisplus.extension.plugins.pagination.Page<PositionListRequestDTO> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(requestDTO.getCurrent(), requestDTO.getSize());
            IPage<PositionListResponseDTO> positions = positionDao.queryPageList(page, wrapper);
            map.put("success", true);
            map.put("data", positions);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> toggleStatus(TogglePositionRequestDTO requestDTO) {
        Map<String, Object> map = new HashMap<>(2);
        Integer status = requestDTO.getStatus();
        Integer positionId = requestDTO.getPositionId();
        try {
            if (1 == status) {
                positionDao.updateFailureStatusById(positionId);

            } else {
                positionDao.updateSuccessStatusById(positionId);
            }
            map.put("success", true);
        } catch (Exception e) {
            //TODO:应该抛出自定义异常
            e.printStackTrace();
            throw e;
        }
        return map;
    }
}
