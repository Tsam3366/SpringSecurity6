package com.sam.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"com.sam.security.entity"})
@EnableJpaRepositories(basePackages = {"com.sam.security.repo"})
@ComponentScan(basePackages = {"com.sam.security.config","com.sam.security.controller","com.sam.security.service"})
@SpringBootApplication
public class SpringSecurityLatestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityLatestApplication.class, args);
	}

}
