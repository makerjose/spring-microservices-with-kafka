package com.josemaker.order_service.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "order")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(length = 15)
    private Long productId;

    @Column(length = 15)
    private Double customerName;

    @Column(length = 30)
    private String customerEmail;

    @Column(length = 10)
    private Integer quantity;

    @Column(length = 30)
    private Double totalPrice;

    @Column(updatable = false)
    private LocalDateTime orderDate;
}


