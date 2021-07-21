package com.hemant.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.hemant.db.repository")
@SpringBootApplication
public class SpringbootMysqlApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbootMysqlApplication.class, args);
	}
}
