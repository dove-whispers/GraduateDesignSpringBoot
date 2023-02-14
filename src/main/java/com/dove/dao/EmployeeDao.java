package com.dove.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dove.dto.EmployeeDTO;
import com.dove.entity.Employee;
import org.apache.ibatis.annotations.Param;


/**
 * 员工Dao
 *
 * @author dove_whispers
 * @date 2023-02-10
 */
public interface EmployeeDao extends BaseMapper<Employee> {
    /**
     * 通过账户名密码联合查询
     *
     * @param userName
     * @param password
     * @return
     */
    EmployeeDTO queryEmInfoByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);
}

