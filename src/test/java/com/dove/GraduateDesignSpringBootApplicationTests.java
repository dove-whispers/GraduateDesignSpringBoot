package com.dove;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dove.dao.DepartmentDao;
import com.dove.dao.EmployeeDao;
import com.dove.dto.EmployeeDTO;
import com.dove.dto.requestDTO.DepartmentListRequestDTO;
import com.dove.dto.responseDTO.DepartmentListResponseDTO;
import com.dove.property.KeyProperty;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class GraduateDesignSpringBootApplicationTests {
    @Resource
    private EmployeeDao employeeDao;
    @Resource
    private DepartmentDao departmentDao;
    @Resource
    private KeyProperty keyProperty;

    @Test
    void contextLoads() {
        EmployeeDTO employeeDTO = employeeDao.queryEmInfoByUserNameAndPassword("dove_whispers", "123456");
        System.out.println(employeeDTO);
    }

    @Test
    void queryPageList() {
        QueryWrapper<DepartmentListRequestDTO> wrapper = new QueryWrapper<>();
        Page<DepartmentListRequestDTO> page = new Page<>(1, 5);
        wrapper.like("name", "测试");
        wrapper.eq("status", 1);
        IPage<DepartmentListResponseDTO> iPage = departmentDao.queryPageList(page, wrapper);
        System.out.println("当前页码值：" + iPage.getCurrent());
        System.out.println("每页显示数：" + iPage.getSize());
        System.out.println("一共多少页：" + iPage.getPages());
        System.out.println("一共多少条数据：" + iPage.getTotal());
        System.out.println("数据：" + iPage.getRecords());
    }

    @Test
    void common() {
        System.out.println(keyProperty);
    }

}
