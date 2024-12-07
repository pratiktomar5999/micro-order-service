package com.micro.order_service.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.micro.order_service.client.InventoryClient;
import com.micro.order_service.model.Order;
import com.micro.order_service.repo.OrderRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private OrderRepo orderRepo;
    private final InventoryClient inventoryClient;
    
    public void placeOrder(Order order){
        var isInStock = inventoryClient.isInStock(order.getSkuCode(), order.getQuantity());
        if(isInStock){
            order.setId(null);
            order.setOrderNumber(UUID.randomUUID().toString());

            orderRepo.save(order);
        }else{
            throw new RuntimeException("Product with skucode " + order.getSkuCode()+" is not in stock.");
        }

    }
}
