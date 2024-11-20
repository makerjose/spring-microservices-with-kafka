package com.josemaker.product_service.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(length = 30)
    private String name;

    @Column(length = 15)
    private Double price;

    @Column(length = 10)
    private Integer quantity;

    @Column(length = 30)
    private String type;
}
