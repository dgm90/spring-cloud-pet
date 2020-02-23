package com.derzhavets;

import com.derzhavets.configuration.OvertimesServiceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableEurekaClient
@Import(OvertimesServiceConfiguration.class)
public class OvertimesServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(OvertimesServiceApplication.class, args);
	}
}
