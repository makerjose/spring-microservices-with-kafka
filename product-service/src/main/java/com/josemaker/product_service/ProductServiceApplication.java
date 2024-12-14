package com.josemaker.product_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

//@SpringBootApplication
@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = {"com.josemaker.product_service"})
public class ProductServiceApplication {

//	@Value("${kafka.topics.product-created.name}")
//	private String productCreatedTopic;

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	// test message
//	@Bean
//	CommandLineRunner commandLineRunner(KafkaTemplate<String, String> kafkaTemplate) {
//		return args -> {
//			kafkaTemplate.send(productCreatedTopic, "hello kafka test");
//		};
//	}
}