package com.josemaker.order_service.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String orderCreatedTopic;

    // Constructor for dependency injection
    public KafkaProducerService(
            KafkaTemplate<String, Object> kafkaTemplate,
            @Value("${kafka.topics.order-created.name}") String orderCreatedTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.orderCreatedTopic = orderCreatedTopic;
    }

    public void sendOrderCreatedEvent(Object message) {
        kafkaTemplate.send(orderCreatedTopic, message);
    }
}
