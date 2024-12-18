package com.josemaker.order_service.services;

import com.josemaker.order_service.entities.OrderEntity;
import com.josemaker.order_service.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public void createOrder(OrderEntity orderEntity){
        orderRepository.save(orderEntity);
    }

    // find order by orderId
    public Optional<OrderEntity> findByOrderId(Long orderId) {
        return orderRepository.findByOrderId(orderId);
    }
}
