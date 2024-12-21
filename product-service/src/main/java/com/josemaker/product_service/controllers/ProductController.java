package com.josemaker.product_service.controllers;

import com.josemaker.product_service.dtos.OrderCreatedDto;
import com.josemaker.product_service.dtos.OrderProcessedDto;
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

import java.time.LocalDateTime;
import java.util.List;

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

            // Check Kafka connectivity
//            if (!kafkaProducerService.isKafkaConnected()) {
//                request.setMessage("Error!: Failed to connect to Kafka");
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(request);
//            }

            // Map DTO to Entity
            ProductEntity productEntity = new ProductEntity();
            productEntity.setName(request.getName());
            productEntity.setType(request.getType());
            productEntity.setPrice(request.getPrice());
            productEntity.setQuantity(request.getQuantity());

            // Save to DB
            productService.createProduct(productEntity);

            // Send Kafka event after successful save
            kafkaProducerService.sendProductCreatedEvent(productEntity);

            // Log success
            String loggerStr = String.format("Success, product created: %s, Type: %s, Price: %.2f, Quantity: %d",
                    productEntity.getName(), productEntity.getType(), productEntity.getPrice(), productEntity.getQuantity());
            logger.info("Success, product created: " + loggerStr);

            // Set success message in DTO
            request.setMessage("Success, product created");
            return ResponseEntity.ok(request);

        } catch (Exception e) {
            logger.error("Error creating product: ", e);
            request.setMessage("Error!: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(request);
        }
    }

    // endpoint for fetching all products
    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProductEntity>> getAllProducts() {
        try {
            List<ProductEntity> branches = productService.getAllProducts();
            logger.info("Successfully fetched all products: " + branches);
            return ResponseEntity.ok(branches);
        } catch (Exception e) {
            // server logs
            logger.warn("Error! " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // endpoint to update product quantity
    @PatchMapping("/updateQuantity")
    public ResponseEntity<OrderCreatedDto> updateQuantity(@RequestBody OrderCreatedDto orderCreatedDto) {
        try {
            Long productId = orderCreatedDto.getProductId();
            Integer orderQuantity = orderCreatedDto.getQuantity();

            // Fetch product from inventory
            ProductEntity productEntity = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product with ID " + productId + " not found"));

            // Deduct order quantity from inventory
            if (productEntity.getQuantity() < orderQuantity) {
                throw new RuntimeException("Insufficient stock for Product ID: " + productId);
            }

            productEntity.setQuantity(productEntity.getQuantity() - orderQuantity);

            // Update the inventory
            productRepository.save(productEntity);

            logger.info("Product quantity updated successfully for Product ID: {}", productId);

            // Send OrderProcessedEvent to Kafka
            OrderProcessedDto orderProcessedDto = new OrderProcessedDto();
            orderProcessedDto.setMessage("Order processed successfully");
            orderProcessedDto.setCustomerName(orderCreatedDto.getCustomerName());
            orderProcessedDto.setCustomerEmail(orderCreatedDto.getCustomerEmail());
            orderProcessedDto.setQuantity(orderCreatedDto.getQuantity());
            orderProcessedDto.setTotalPrice(orderCreatedDto.getTotalPrice());
            orderProcessedDto.setProcessedDate(LocalDateTime.now().toString());

            // send kafka event, order processed
            kafkaProducerService.sendOrderProcessedEvent(orderProcessedDto);

            logger.info("Order processed event sent for Product ID: {}", productId);

            // Update response DTO message
            orderCreatedDto.setMessage("Quantity updated successfully");
            return ResponseEntity.ok(orderCreatedDto);

        } catch (Exception e) {
            logger.error("Error updating product quantity: ", e);
            orderCreatedDto.setMessage("Error updating product quantity: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(orderCreatedDto);
        }
    }

}
