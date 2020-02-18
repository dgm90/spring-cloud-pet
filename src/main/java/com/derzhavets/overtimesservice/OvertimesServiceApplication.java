package com.derzhavets.overtimesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class OvertimesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OvertimesServiceApplication.class, args);
	}

}
