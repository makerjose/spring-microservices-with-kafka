package com.josemaker.order_service.controllers;

import com.josemaker.order_service.dtos.KafkaEventDto;
import com.josemaker.order_service.dtos.OrderRequestDto;
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
import java.time.format.DateTimeFormatter;
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
    public ResponseEntity<OrderRequestDto> createProduct(@RequestBody OrderRequestDto request) {
        try {
            if (request == null) {
                request.setMessage("Bad Request!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(request);
            }

            OrderEntity orderEntity = new OrderEntity();

            // Set data to entity
            orderEntity.setProductId(request.getProductId());
            orderEntity.setCustomerName(request.getCustomerName());
            orderEntity.setCustomerEmail(request.getCustomerEmail());
            orderEntity.setQuantity(request.getQuantity());
//            orderEntity.setOrderDate(LocalDateTime.now().toString());
            orderEntity.setOrderDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

            // Save to DB
            orderService.createOrder(orderEntity);

            // Map entity to DTO for Kafka event
            KafkaEventDto eventDto = mapEntityToDto(orderEntity);

            // Send Kafka event after successful save
            kafkaProducerService.sendOrderCreatedEvent(eventDto);

            // Populate response DTO
            request.setOrderDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
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

    // Method to map entity to DTO
    private KafkaEventDto mapEntityToDto(OrderEntity entity) {
        KafkaEventDto dto = new KafkaEventDto();
        dto.setOrderId(entity.getOrderId());
        dto.setProductId(entity.getProductId());
        dto.setCustomerName(entity.getCustomerName());
        dto.setCustomerEmail(entity.getCustomerEmail());
        dto.setQuantity(entity.getQuantity());
        dto.setOrderDate(entity.getOrderDate());
        return dto;
    }
}
