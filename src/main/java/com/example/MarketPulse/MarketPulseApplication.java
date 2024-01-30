package com.example.MarketPulse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class MarketPulseApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketPulseApplication.class, args);
	}

}
