package com.dove;

import com.dove.dao.EmployeeDao;
import com.dove.dto.EmployeeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.annotation.Resources;

@SpringBootTest
class GraduateDesignSpringBootApplicationTests {
	@Resource
	private EmployeeDao employeeDao;
	@Test
	void contextLoads() {
		EmployeeDTO employeeDTO = employeeDao.queryEmInfoByUserNameAndPassword("dove_whispers", "123456");
		System.out.println(employeeDTO);
	}

}
