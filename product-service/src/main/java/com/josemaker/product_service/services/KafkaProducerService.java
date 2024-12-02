package com.josemaker.product_service.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    //Topic name for the Product service events
    @Value("${product.topic.name}")
    private String productCreated;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public boolean isKafkaConnected() {
        try {
            kafkaTemplate.send(productCreated, "Test Connection").get();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void sendProductCreatedEvent(String message) {
        kafkaTemplate.send(productCreated, message);
    }
}
