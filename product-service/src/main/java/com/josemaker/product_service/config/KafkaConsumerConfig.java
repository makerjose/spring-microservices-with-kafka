package com.josemaker.product_service.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

public class KafkaConsumerConfig {
    private final String productCreatedTopicName;
    private final String bootstrapServers;
    private final int partitions;
    private final short replicas;

    // constructor dependency injection
    // partitions and replicas will default to 1 if not defined
    public KafkaConsumerConfig(
            @Value("${kafka.topics.product-created.name}") String productCreatedTopicName,
            @Value("${spring.kafka.bootstrap-servers}") String bootstrapServers,
            @Value("${kafka.topics.product-created.partitions:3}") int partitions,
            @Value("${kafka.topics.product-created.replicas:1}") short replicas) {

        this.productCreatedTopicName = productCreatedTopicName;
        this.bootstrapServers = bootstrapServers;
        this.partitions = partitions;
        this.replicas = replicas;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();

        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(ConsumerFactory<String, String> consumerFactory) {
        return new KafkaTemplate<>(consumerFactory);
    }

    public KafkaListenerContainerFactory<
            ConcurrentMessageListenerContainer<String, String>> factory (
            ConsumerFactory<String, String> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
