package com.josemaker.product_service.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Value("${product.topic.name}")
    private String productCreatedTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate, KafkaAdmin kafkaAdmin) {
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



//package com.josemaker.product_service.services;
//
//import org.apache.kafka.clients.admin.AdminClient;
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//
//@Service
//public class KafkaProducerService {
//
//    private final KafkaTemplate<String, String> kafkaTemplate;
//    private final AdminClient adminClient;
//
//    @Autowired
//    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate, AdminClient adminClient) {
//        this.kafkaTemplate = kafkaTemplate;
//        this.adminClient = adminClient;
//    }
//
//    public boolean isKafkaConnected() {
//        try {
//            kafkaTemplate.send(productCreated(), "Test Connection").get();
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    public void sendProductCreatedEvent(String message) {
//        kafkaTemplate.send(productCreated(), message);
//    }
//
//    public void createTopicIfNotExists() {
//        try {
//            if (!adminClient.listTopics().names().get().contains(productCreated())) {
//                NewTopic topic = new NewTopic(productCreated(), 3, (short) 1);
//                adminClient.createTopics(Collections.singletonList(topic));
//                System.out.println("Kafka topic created: " + productCreated());
//            } else {
//                System.out.println("Kafka topic already exists: " + productCreated());
//            }
//        } catch (Exception e) {
//            System.err.println("Failed to create Kafka topic: " + e.getMessage());
//        }
//    }
//
//    private String productCreated() {
//        return "productCreated";
//    }
//}
