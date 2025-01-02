package com.josemaker.order_service.dtos;

import lombok.Data;

@Data
public class KafkaEventDto {
    private Long orderId;
    private Long productId;
    private String customerName;
    private String customerEmail;
    private Integer quantity;
    private String orderDate;
}
