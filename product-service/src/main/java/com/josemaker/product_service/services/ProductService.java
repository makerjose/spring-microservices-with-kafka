package com.josemaker.product_service.services;

import com.josemaker.product_service.entities.ProductEntity;
import com.josemaker.product_service.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    KafkaProducerService kafkaProducerService;

    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public void createProduct(ProductEntity productEntity){
        productRepository.save(productEntity);

        // Send Kafka event after successful save
        String kafkaMessage = String.format("Product created: %s", productEntity);
        kafkaProducerService.sendProductCreatedEvent(kafkaMessage);
    }

    // find product by productId
    public Optional<ProductEntity> findByProductId(Long productId) {
        return productRepository.findByProductId(productId);
    }
}
