package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.demo.dao")
public class ComicLeeBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComicLeeBackEndApplication.class, args);
	}

}
