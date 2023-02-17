package com.dove;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dove.dao.DepartmentDao;
import com.dove.dao.EmployeeDao;
import com.dove.dto.EmployeeDTO;
import com.dove.dto.requestDTO.DepartmentListRequestDTO;
import com.dove.dto.responseDTO.DepartmentListResponseDTO;
import com.dove.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class GraduateDesignSpringBootApplicationTests {
    @Resource
    private EmployeeDao employeeDao;
    @Resource
    private DepartmentDao departmentDao;

    @Test
    void contextLoads() {
        EmployeeDTO employeeDTO = employeeDao.queryEmInfoByUserNameAndPassword("dove_whispers", "123456");
        System.out.println(employeeDTO);
    }

    @Test
    void test1() {
        QueryWrapper<DepartmentListRequestDTO> wrapper = new QueryWrapper<>();
        Page<DepartmentListRequestDTO> page = new Page<>(1, 10);
        wrapper.like("name", "教");
        IPage<DepartmentListResponseDTO> iPage = departmentDao.queryPageList(page, wrapper);
        System.out.println(iPage);
    }

}
