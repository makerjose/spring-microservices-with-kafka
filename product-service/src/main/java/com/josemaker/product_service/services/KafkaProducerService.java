package com.josemaker.product_service.services;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Properties;

@Service
public class KafkaProducerService {

    @Value("${product.topic.name}")
    private String productCreated;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

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

//    public void createTopicIfNotExists() {
//        Properties configs = new Properties();
//        configs.put("bootstrap.servers", bootstrapServers);
//
//        try (AdminClient adminClient = AdminClient.create(configs)) {
//            //check if the topic exists
//            if (!adminClient.listTopics().names().get().contains(productCreated)) {
//                //create topic with 3 partitions and 1 replica
//                NewTopic topic = new NewTopic(productCreated, 3, (short) 1);
//                adminClient.createTopics(Collections.singletonList(topic));
//                System.out.println("Kafka topic created: " + productCreated);
//            } else {
//                System.out.println("Kafka topic already exists: " + productCreated);
//            }
//        } catch (Exception e) {
//            System.err.println("Failed to create Kafka topic: " + e.getMessage());
//        }
//    }
}
