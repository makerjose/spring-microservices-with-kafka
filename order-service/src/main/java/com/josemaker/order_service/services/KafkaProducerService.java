package com.josemaker.order_service.services;

import com.josemaker.order_service.dtos.KafkaEventDto;
import com.josemaker.order_service.entities.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topics.order-created.name}")
    private String orderCreatedTopic;

    @Autowired
    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // this can be mapped to a dto instead of sending the entity directly. z
    public void sendOrderCreatedEvent(KafkaEventDto kafkaEventDto) {
        kafkaTemplate.send(orderCreatedTopic, kafkaEventDto);
    }
}

