package com.josemaker.product_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.EnableKafka;

//@SpringBootApplication
@EnableKafka
@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = {"com.josemaker.product_service"})
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

}