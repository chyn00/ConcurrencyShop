package com.shop.concurrency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ConcurrencyShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConcurrencyShopApplication.class, args);
	}

}
