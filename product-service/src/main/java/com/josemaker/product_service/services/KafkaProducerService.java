package com.josemaker.product_service.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Value("${kafka.topics.product-created.name}")
    private String productCreatedTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public boolean isKafkaConnected() {
        try {
            kafkaTemplate.send(productCreatedTopic, "Test Connection").get();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void sendProductCreatedEvent(String message) {
        kafkaTemplate.send(productCreatedTopic, message);
    }

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

}