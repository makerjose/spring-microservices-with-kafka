package com.josemaker.order_service.dtos;

import lombok.Data;

@Data
public class OrderDto {
    private String message;
    private Long productId;
    private String name;
    private Double price;
    private Integer quantity;
    private String type;
}
