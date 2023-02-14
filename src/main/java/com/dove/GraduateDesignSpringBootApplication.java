package com.dove;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 毕业设计弹簧启动应用程序
 *
 * @author dove_whispers
 * @date 2023-02-06
 */
@SpringBootApplication
@EnableScheduling
@EnableCaching
@MapperScan("com.dove.dao")
public class GraduateDesignSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraduateDesignSpringBootApplication.class, args);
	}

}
