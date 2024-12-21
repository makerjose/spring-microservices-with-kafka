package com.josemaker.product_service.dtos;

import lombok.Data;

@Data
public class OrderProcessedDto {
    private String message;
    private String customerName;
    private String customerEmail;
    private Integer quantity;
    private Double totalPrice;
    private String processedDate;
}
