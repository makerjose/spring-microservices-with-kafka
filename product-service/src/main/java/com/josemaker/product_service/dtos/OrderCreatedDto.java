package com.josemaker.product_service.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderCreatedDto {
    private Long orderId;
    private Long productId;
    private String customerName;
    private String customerEmail;
    private Integer quantity;
    private String orderDate;
}
