package com.josemaker.order_service.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "order_tb")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(length = 15, nullable = false)
    private Long productId;

    @Column(length = 20, nullable = false)
    private String customerName;

    @Column(length = 30, nullable = false)
    private String customerEmail;

    @Column(length = 10, nullable = false)
    private Integer quantity;

    @Column(length = 20, nullable = false)
    private Double totalPrice;

    @Column(nullable = false, updatable = false)
    private LocalDateTime orderDate;

//    // generate the date before saving to DB
//    @PrePersist
//    public void prePersist() {
//        this.orderDate = LocalDateTime.now();
//    }
}


