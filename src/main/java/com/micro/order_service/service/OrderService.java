package com.micro.order_service.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.micro.order_service.model.Order;
import com.micro.order_service.repo.OrderRepo;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;
    
    public void placeOrder(Order order){
        order.setId(null);
        order.setOrderNumber(UUID.randomUUID().toString());

        orderRepo.save(order);

    }
}
