package com.josemaker.order_service.services;

import com.josemaker.order_service.entities.OrderEntity;
import com.josemaker.order_service.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public List<OrderEntity> getAllProducts() {
        return orderRepository.findAll();
    }

//    private final KafkaTemplate<String, Product> kafkaTemplate;
//
//    public void productProducer(KafkaTemplate<String, Product> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    public void sendProductUpdate(Product product) {
//        kafkaTemplate.send("product-topic", product);
//    }

    @Transactional
    public void createProduct(OrderEntity orderEntity){
        orderRepository.save(orderEntity);
    }

    // find product by productId
    public Optional<OrderEntity> findByProductId(Long productId) {
        return orderRepository.findByProductId(productId);
    }
}
