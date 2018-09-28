package com.datacrawling.kraken;

import java.time.Instant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootJooqMysqlApplication {

	public static Instant startTime;

	public static void main(String[] args) {
		startTime = Instant.now();
		SpringApplication.run(SpringBootJooqMysqlApplication.class, args);
	}

}
