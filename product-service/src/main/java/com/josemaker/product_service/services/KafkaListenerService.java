package com.josemaker.product_service.services;

import com.josemaker.product_service.dtos.OrderCreatedDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaListenerService.class);

    @Autowired
    private ProductService productService;

    // consume order event, map to dto then update the inventory using
    @KafkaListener(topics = "${kafka.topics.order-created.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeOrderEvent(OrderCreatedDto orderCreatedDto) {
        try {
            logger.info("Received Order Event: {}", orderCreatedDto);

            // Process order and update inventory
            productService.updateProductQuantity(orderCreatedDto);

        } catch (Exception e) {
            logger.error("Error processing order event: {}", e.getMessage(), e);
        }
    }
}
