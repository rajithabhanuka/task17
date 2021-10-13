package com.code.task17;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class Task17Application {

	public static void main(String[] args) {
		SpringApplication.run(Task17Application.class, args);
	}

}
