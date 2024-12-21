package com.josemaker.product_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    private final String productCreatedTopicName;
    private final String bootstrapServers;
    private final int partitions;
    private final short replicas;

    // using constructor dependency injection instead of the autowired @value annotation on variables
    // partitions and replicas will default to 1 if not defined
    public KafkaProducerConfig(
            @Value("${kafka.topics.product-created.name}") String productCreatedTopicName,
            @Value("${spring.kafka.bootstrap-servers}") String bootstrapServers,
            @Value("${kafka.topics.product-created.partitions}") int partitions,
            @Value("${kafka.topics.product-created.replicas}") short replicas) {

        this.productCreatedTopicName = productCreatedTopicName;
        this.bootstrapServers = bootstrapServers;
        this.partitions = partitions;
        this.replicas = replicas;
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public NewTopic orderCreatedTopic() {
        System.out.println("Initializing topic creation for: " + productCreatedTopicName);
        return TopicBuilder.name(productCreatedTopicName)
                .partitions(partitions)
                .replicas(replicas)
                .build();
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
