package com.josemaker.product_service.repositories;

import com.josemaker.product_service.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByProductId(Long productId);
}
