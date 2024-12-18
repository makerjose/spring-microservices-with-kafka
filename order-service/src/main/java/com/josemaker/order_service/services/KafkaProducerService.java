package com.josemaker.order_service.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String orderCreatedTopic;

    // constructor for dependency injection
    public KafkaProducerService(
            KafkaTemplate<String, String> kafkaTemplate,
            @Value("${kafka.topics.order-created.name") String orderCreatedTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.orderCreatedTopic = orderCreatedTopic;
    }

//    // check for connectivity before triggering REST endpoints
//    public boolean isKafkaConnected() {
//        try {
//            kafkaTemplate.send(orderCreatedTopic, "Test Connection").get();
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }

    public void sendOrderCreatedEvent(String message) {
        kafkaTemplate.send(orderCreatedTopic, message);
    }
}
