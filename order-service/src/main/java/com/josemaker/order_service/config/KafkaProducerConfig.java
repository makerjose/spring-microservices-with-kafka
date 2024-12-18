package com.josemaker.order_service.config;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

public class KafkaProducerConfig {

    private final String orderCreatedTopicName;
    private final String bootstrapServers;
    private final int partitions;
    private final short replicas;

    // dependency injection
    public KafkaProducerConfig(
            @Value("${kafka.topics.product-created.name}") String productCreatedTopicName,
            @Value("${spring.kafka.bootstrap-servers}") String bootstrapServers,
            @Value("${kafka.topics.product-created.partitions}") int partitions,
            @Value("${kafka.topics.product-created.replicas}") short replicas) {

        this.orderCreatedTopicName = productCreatedTopicName;
        this.bootstrapServers = bootstrapServers;
        this.partitions = partitions;
        this.replicas = replicas;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public NewTopic productCreatedTopic() {
        System.out.println("Initializing topic creation for: " + orderCreatedTopicName);
        return TopicBuilder.name(orderCreatedTopicName)
                .partitions(partitions)
                .replicas(replicas)
                .build();
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
