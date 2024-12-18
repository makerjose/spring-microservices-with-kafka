package com.josemaker.order_service.controllers;

import com.josemaker.order_service.dtos.OrderDto;
import com.josemaker.order_service.entities.OrderEntity;
import com.josemaker.order_service.repositories.OrderRepository;
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
@RequestMapping("/api/v1/product")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    // endpoint for getting all products
    @PostMapping("/createProduct")
    public ResponseEntity<OrderDto> createProduct(@RequestBody OrderDto request) {
        try {
            if (request == null) {
                request.setMessage("Bad Request! ");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(request);
            }

            // Fetch the OrderEntity based on the provided productId in the request, to verify existence in DB
            Optional<OrderEntity> productOptional = orderRepository.findByOrderId(request.getProductId());
            if (productOptional.isPresent()) {
                request.setMessage("Product already exist with productId: " + request.getProductId());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(request);
            }

            OrderEntity orderEntity = new OrderEntity();

            // set data to entity
//            orderEntity.setName(request.getName());
//            orderEntity.setType(request.getType());
//            orderEntity.setPrice(request.getPrice());
//            orderEntity.setQuantity(request.getQuantity());

            // Save to DB
            orderService.createProduct(orderEntity);

            // Set success message to dto
            request.setMessage("Product created successfully");

            // Server logs variable
            String loggerStr = String.format("Name: %s, Type: %s, Price: %s, Quantity: %s,",
                    request.getName(), request.getType(), request.getPrice(), request.getQuantity());

            logger.info("Product created successfully: " + loggerStr);
            return ResponseEntity.ok(request);

        } catch (Exception e) {
            // Log server errors
            logger.warn("Exception: " + e.getMessage());
            // Api response
            request.setMessage("Error: " + e.getMessage() + " " + e.getClass().getName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(request);
        }
    }

//     endpoint for fetching all products
    @GetMapping("/getAllProducts")
    public ResponseEntity<List<OrderEntity>> getAllProducts() {
        try {
            List<OrderEntity> branches = orderService.getAllProducts();
            logger.info("Success on fetch | All products: " + branches);
            return ResponseEntity.ok(branches);
        } catch (Exception e) {
            // server logs
            logger.warn("Exception!: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
