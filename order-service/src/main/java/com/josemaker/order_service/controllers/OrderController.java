package com.josemaker.order_service.controllers;

import com.josemaker.order_service.dtos.OrderDto;
import com.josemaker.order_service.entities.OrderEntity;
import com.josemaker.order_service.repositories.OrderRepository;
import com.josemaker.order_service.services.KafkaProducerService;
import com.josemaker.order_service.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    KafkaProducerService kafkaProducerService;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    // endpoint for getting all products
    @PostMapping("/createOrder")
    public ResponseEntity<OrderDto> createProduct(@RequestBody OrderDto request) {
        try {
            if (request == null) {
                request.setMessage("Bad Request! ");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(request);
            }

            OrderEntity orderEntity = new OrderEntity();

            // set data to entity
            orderEntity.setProductId(request.getProductId());
            orderEntity.setCustomerName(request.getCustomerName());
            orderEntity.setCustomerEmail(request.getCustomerEmail());
            orderEntity.setQuantity(request.getQuantity());
            orderEntity.setTotalPrice(request.getTotalPrice());
            orderEntity.setOrderDate(request.getOrderDate());

            // Save to DB
            orderService.createOrder(orderEntity);

            // Send Kafka event after successful save
            kafkaProducerService.sendOrderCreatedEvent(orderEntity);

            // Server logs variable
            String loggerStr = String.format("Product ID: %s, Customer Name: %s, Email: %s, Quantity: %s, Price: %s, Date: %s,",
                    request.getProductId(), request.getCustomerName(), request.getCustomerEmail(), request.getQuantity(), request.getTotalPrice(), request.getOrderDate());
            logger.info("Order created successfully: " + loggerStr);

            // Set success message to dto
            request.setMessage("Order created successfully");
            return ResponseEntity.ok(request);

        } catch (Exception e) {
            // Log server errors
            logger.warn("Exception: " + e.getMessage());
            // Api response
            request.setMessage("Error: " + e.getMessage() + " " + e.getClass().getName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(request);
        }
    }

//     endpoint for fetching all orders
    @GetMapping("/getAllOrders")
    public ResponseEntity<List<OrderEntity>> getAllProducts() {
        try {
            List<OrderEntity> branches = orderService.getAllOrders();
            logger.info("Success on fetch | All orders: " + branches);
            return ResponseEntity.ok(branches);
        } catch (Exception e) {
            // server logs
            logger.warn("Exception!: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
