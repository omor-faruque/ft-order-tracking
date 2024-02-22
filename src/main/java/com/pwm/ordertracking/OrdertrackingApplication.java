package com.pwm.ordertracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OrdertrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdertrackingApplication.class, args);
	}

}
