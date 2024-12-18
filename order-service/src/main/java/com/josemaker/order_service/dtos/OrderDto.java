package com.josemaker.order_service.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {
    private String message;
    private Long productId;
    private String customerName;
    private String customerEmail;
    private Double totalPrice;
    private Integer quantity;
    private LocalDateTime orderDate;
}
