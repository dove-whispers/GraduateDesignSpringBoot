package com.dove.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dove.constants.Constants;
import com.dove.dao.DealRecordDao;
import com.dove.dao.ExpenseReportDao;
import com.dove.dao.ExpenseReportDetailDao;
import com.dove.dto.EmployeeDTO;
import com.dove.dto.ExpenseReportDTO;
import com.dove.dto.requestDTO.ExpenseReportDetailRequestDTO;
import com.dove.dto.requestDTO.ExpenseReportMainListRequestDTO;
import com.dove.dto.requestDTO.ExpenseReportRequestDTO;
import com.dove.entity.*;
import com.dove.service.ExpenseReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 报销单表(ExpenseReport)表服务实现类
 *
 * @author dove_whispers
 * @date 2023-03-04
 */
@Service("expenseReportService")
public class ExpenseReportServiceImpl implements ExpenseReportService {
    @Resource
    private ExpenseReportDao expenseReportDao;
    @Resource
    private ExpenseReportDetailDao expenseReportDetailDao;
    @Resource
    private DealRecordDao dealRecordDao;
    @Resource
    private EmployeeServiceImpl employeeService;

    /**
     * 通过ID查询单条数据
     *
     * @param expenseId 主键
     * @return 实例对象
     */
    @Override
    public ExpenseReport queryById(Integer expenseId) {
        return this.expenseReportDao.queryById(expenseId);
    }

    /**
     * 分页查询
     *
     * @param expenseReport 筛选条件
     * @param pageRequest   分页对象
     * @return 查询结果
     */
    @Override
    public Page<ExpenseReport> queryByPage(ExpenseReport expenseReport, PageRequest pageRequest) {
        long total = this.expenseReportDao.count(expenseReport);
        return new PageImpl<>(this.expenseReportDao.queryAllByLimit(expenseReport, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param expenseReport 实例对象
     * @return 实例对象
     */
    @Override
    public ExpenseReport insert(ExpenseReport expenseReport) {
        this.expenseReportDao.insert(expenseReport);
        return expenseReport;
    }

    /**
     * 修改数据
     *
     * @param expenseReport 实例对象
     * @return 实例对象
     */
    @Override
    public ExpenseReport update(ExpenseReport expenseReport) {
        this.expenseReportDao.update(expenseReport);
        return this.queryById(expenseReport.getExpenseId());
    }

    /**
     * 通过主键删除数据
     *
     * @param expenseId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer expenseId) {
        return this.expenseReportDao.deleteById(expenseId) > 0;
    }

    /**
     * 主页查询报销单列表
     *
     * @param requestDTO 请求dto
     * @param emId       登录人id
     * @param department 登录人部门
     * @param position   登录人位置
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    public Map<String, Object> queryMainPageList(ExpenseReportMainListRequestDTO requestDTO, Integer emId, Department department, Position position) {
        Map<String, Object> map = new HashMap<>(2);
        String positionName = position.getPositionName();
        try {
            QueryWrapper<ExpenseReportMainListRequestDTO> queryWrapper = new QueryWrapper<>();
            queryWrapper.like(StrUtil.isNotEmpty(requestDTO.getCause()), "cause", requestDTO.getCause())
                    .inSql(StrUtil.isNotEmpty(requestDTO.getName()), "em_id", "SELECT em_id FROM employee WHERE name LIKE '%" + requestDTO.getName() + "%'")
                    .eq(positionName.equals(Constants.Name.STAFF), "em_id", emId)
                    .inSql(positionName.equals(Constants.Name.DEPARTMENT_MANAGER), "em_id", "SELECT em_id FROM employee WHERE dep_id = " + department.getDepId())
                    .notIn(Objects.nonNull(requestDTO.getStatus()), "status", Constants.Status.PAID, Constants.Status.TERMINATED);
            com.baomidou.mybatisplus.extension.plugins.pagination.Page<ExpenseReportMainListRequestDTO> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(requestDTO.getCurrent(), requestDTO.getSize());
            IPage<ExpenseReportDTO> reports = expenseReportDao.queryMainPageList(page, queryWrapper);
            map.put("success", true);
            map.put("data", reports);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        return map;
    }

    /**
     * 添加报销单
     *
     * @param userInfo   用户信息
     * @param requestDTO 请求dto
     */
    @Override
    @SneakyThrows
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addExpenseReport(EmployeeDTO userInfo, ExpenseReportRequestDTO requestDTO) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Integer emId = userInfo.getEmId();
            Integer nextDealEmId = employeeService.queryNextDealEmId(emId, userInfo.getDepId());
            ExpenseReport expenseReport = new ExpenseReport(null, requestDTO.getCause(), emId, new Date(), DateUtil.nextWeek(), nextDealEmId, requestDTO.getTotalAmount(), Constants.Status.CREATE);
            expenseReport = this.insert(expenseReport);
            Integer expenseId = expenseReport.getExpenseId();
            for (ExpenseReportDetailRequestDTO expenseReportDetailRequestDTO : requestDTO.getExpenseReportDetails()) {
                String image = expenseReportDetailRequestDTO.getImage();
                expenseReportDetailRequestDTO.setImage("");
                ExpenseReportDetail expenseReportDetail = mapper.readValue(mapper.writeValueAsString(expenseReportDetailRequestDTO), ExpenseReportDetail.class);
                expenseReportDetail.setExpensiveId(expenseId);
                expenseReportDetail.setImage(image.getBytes(StandardCharsets.UTF_8));
                expenseReportDetailDao.insert(expenseReportDetail);
            }
            dealRecordDao.insert(new DealRecord(null, expenseId, emId, new Date(), Constants.Way.CREATE, Constants.Result.CREATED, null));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
