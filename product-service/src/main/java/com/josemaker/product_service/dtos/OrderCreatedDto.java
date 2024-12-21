package com.josemaker.product_service.dtos;

import lombok.Data;

@Data
public class OrderCreatedDto {
    private String message;
    private Long productId;
    private String customerName;
    private String customerEmail;
    private Integer quantity;
    private Double totalPrice;
    private String orderDate;
}
