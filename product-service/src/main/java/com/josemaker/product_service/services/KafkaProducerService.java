package com.josemaker.product_service.services;

import com.josemaker.product_service.dtos.OrderProcessedDto;
import com.josemaker.product_service.entities.ProductEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String productCreatedTopic;
    private final String orderProcessedTopic;

    // constructor for dependency injection
    public KafkaProducerService(
            KafkaTemplate<String, Object> kafkaTemplate,
            @Value("${kafka.topics.product-created.name}") String productCreatedTopic,
            @Value("${kafka.topics.order-processed.name}") String orderProcessedTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.productCreatedTopic = productCreatedTopic;
        this.orderProcessedTopic = orderProcessedTopic;
    }

    public void sendProductCreatedEvent(ProductEntity productEntity) {
        kafkaTemplate.send(productCreatedTopic, productEntity);
    }

    // to be processed by the email service
    public void sendOrderProcessedEvent(OrderProcessedDto orderProcessedDto) {
        kafkaTemplate.send(orderProcessedTopic, orderProcessedDto);
    }
}







// check for connectivity before triggering REST endpoints
//    public boolean isKafkaConnected() {
//        try {
//            kafkaTemplate.send(productCreatedTopic, "Test Connection").get();
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }



//    public void createTopicIfNotExists() {
//        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
//            var topicNames = adminClient.listTopics().names().get();
//            if (!topicNames.contains(productCreatedTopic)) {
//                NewTopic topic = new NewTopic(productCreatedTopic, 3, (short) 1);
//                adminClient.createTopics(Collections.singletonList(topic));
//                System.out.println("Kafka topic created: " + productCreatedTopic);
//            } else {
//                System.out.println("Kafka topic already exists: " + productCreatedTopic);
//            }
//        } catch (ExecutionException | InterruptedException e) {
//            System.err.println("Error during Kafka topic creation: " + e.getMessage());
//        }
//    }