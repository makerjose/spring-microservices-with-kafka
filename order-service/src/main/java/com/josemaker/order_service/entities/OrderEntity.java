package com.josemaker.order_service.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "order")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(length = 15)
    private String productId;

    @Column(length = 15)
    private Double customerId;

    @Column(length = 10)
    private Integer quantity;

    @Column(length = 30)
    private Double totalPrice;
}


//@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//@JoinColumn(name = "customerSfId", referencedColumnName = "customerSfId")
//private CustomerEntity customerSfId;
