package com.josemaker.order_service.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {
    private String message;
    private Long productId;
    private String customerName;
    private String customerEmail;
    private Integer quantity;
    private Double totalPrice;
    private LocalDateTime orderDate;
}
