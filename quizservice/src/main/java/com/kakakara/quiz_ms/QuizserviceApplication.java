package com.kakakara.quiz_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class QuizserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizserviceApplication.class, args);
	}

}
