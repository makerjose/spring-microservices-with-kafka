//package com.josemaker.product_service.config;
//
//import com.josemaker.product_service.services.KafkaProducerService;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class KafkaInitializer implements CommandLineRunner {
//
//    private final KafkaProducerService kafkaProducerService;
//
//    //CommandLineRunner is executed automatically after the Spring Application Context is loaded. This ensures that:
//    // Kafka topic initialization logic is run at application startup
//    // & topic is created before any messages are published.
//    public KafkaInitializer(KafkaProducerService kafkaProducerService) {
//        this.kafkaProducerService = kafkaProducerService;
//    }
//
//    @Override
//    public void run(String... args) {
//        kafkaProducerService.createTopicIfNotExists();
//    }
//}