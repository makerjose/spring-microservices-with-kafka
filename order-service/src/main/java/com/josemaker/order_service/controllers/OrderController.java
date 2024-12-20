package com.josemaker.order_service.controllers;

import com.josemaker.order_service.dtos.OrderDto;
import com.josemaker.order_service.entities.OrderEntity;
import com.josemaker.order_service.services.KafkaProducerService;
import com.josemaker.order_service.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    KafkaProducerService kafkaProducerService;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    // endpoint for creating order
    @PostMapping("/createOrder")
    public ResponseEntity<OrderDto> createProduct(@RequestBody OrderDto request) {
        try {
            if (request == null) {
                request.setMessage("Bad Request!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(request);
            }

            OrderEntity orderEntity = new OrderEntity();

            // Set data to entity
            LocalDateTime now = LocalDateTime.now(); // Generate the order date
            orderEntity.setProductId(request.getProductId());
            orderEntity.setCustomerName(request.getCustomerName());
            orderEntity.setCustomerEmail(request.getCustomerEmail());
            orderEntity.setQuantity(request.getQuantity());
            orderEntity.setTotalPrice(request.getTotalPrice());
            orderEntity.setOrderDate(now);

            // Save to DB
            orderService.createOrder(orderEntity);

            // Send Kafka event after successful save
            kafkaProducerService.sendOrderCreatedEvent(orderEntity);

            // Populate response DTO
            request.setOrderDate(now); // same date that was saved to DB
            request.setMessage("Order created successfully");

            // Log success
            logger.info("Order created successfully: {}", orderEntity);

            return ResponseEntity.ok(request);

        } catch (Exception e) {
            // Log server errors
            logger.warn("Exception: {}", e.getMessage(), e);
            request.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(request);
        }
    }

    // endpoint for fetching all orders
    @GetMapping("/getAllOrders")
    public ResponseEntity<List<OrderEntity>> getAllProducts() {
        try {
            List<OrderEntity> orders = orderService.getAllOrders();
            logger.info("Success on fetch | All orders: " + orders);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            // server logs
            logger.warn("Exception!: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
