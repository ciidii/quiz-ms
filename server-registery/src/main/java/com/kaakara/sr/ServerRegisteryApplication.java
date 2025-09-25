package com.kaakara.sr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServerRegisteryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerRegisteryApplication.class, args);
	}

}
