package com.disl.startercore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@EntityScan(basePackages = {"com.disl"})
@ComponentScan(basePackages = {"com.disl"})
@EnableJpaRepositories(basePackages = {"com.disl"})
@SpringBootApplication
public class SpringBootMultiModuleStarterApplication {
	public static final Logger logger = LogManager.getLogger();

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMultiModuleStarterApplication.class, args);
	}
}
