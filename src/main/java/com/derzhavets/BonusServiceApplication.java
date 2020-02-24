package com.derzhavets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class BonusServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(BonusServiceApplication.class, args);
	}
}
