package com.josemaker.product_service.controllers;

import com.josemaker.product_service.dtos.ProductDto;
import com.josemaker.product_service.entities.ProductEntity;
import com.josemaker.product_service.repositories.ProductRepository;
import com.josemaker.product_service.services.KafkaProducerService;
import com.josemaker.product_service.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    KafkaProducerService kafkaProducerService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    // endpoint for creating product
    @PostMapping("/createProduct")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto request) {
        try {
            if (request == null) {
                request.setMessage("Bad Request!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(request);
            }

            // Check if product already exists
            Optional<ProductEntity> productOptional = productRepository.findByProductId(request.getProductId());
            if (productOptional.isPresent()) {
                request.setMessage("Product already exists with productId: " + request.getProductId());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(request);
            }

            // Map DTO to Entity
            ProductEntity productEntity = new ProductEntity();
            productEntity.setName(request.getName());
            productEntity.setType(request.getType());
            productEntity.setPrice(request.getPrice());
            productEntity.setQuantity(request.getQuantity());

            // Save to DB
            productService.createProduct(productEntity);

            // Send Kafka event
            String kafkaMessage = String.format("Product created: Name: %s, Type: %s, Price: %.2f, Quantity: %d",
                    productEntity.getName(), productEntity.getType(), productEntity.getPrice(), productEntity.getQuantity());
            kafkaProducerService.sendProductCreatedEvent(kafkaMessage);

            // Set success message in DTO
            request.setMessage("Product created successfully");
            return ResponseEntity.ok(request);

        } catch (Exception e) {
            logger.error("Error creating product: ", e);
            request.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(request);
        }
    }


//     endpoint for fetching all products
    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProductEntity>> getAllProducts() {
        try {
            List<ProductEntity> branches = productService.getAllProducts();
            logger.info("Success on fetch | All products: " + branches);
            return ResponseEntity.ok(branches);
        } catch (Exception e) {
            // server logs
            logger.warn("Error! " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
