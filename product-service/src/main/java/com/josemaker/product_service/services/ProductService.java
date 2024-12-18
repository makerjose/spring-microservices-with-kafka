package com.josemaker.product_service.services;

import com.josemaker.product_service.entities.ProductEntity;
import com.josemaker.product_service.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Transactional
    public void createProduct(ProductEntity productEntity) {
        productRepository.save(productEntity);
    }

    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<ProductEntity> findByProductId(Long productId) {
        return productRepository.findByProductId(productId);
    }
}
