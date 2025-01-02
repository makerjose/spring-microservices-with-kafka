package com.josemaker.order_service.dtos;

import lombok.Data;

@Data
public class OrderRequestDto {
    private String message;
    private Long productId;
    private String customerName;
    private String customerEmail;
    private Integer quantity;
    private String orderDate;

}
