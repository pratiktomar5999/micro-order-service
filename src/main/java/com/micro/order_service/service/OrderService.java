package com.micro.order_service.service;

import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.micro.event.OrderEvent;
import com.micro.order_service.client.InventoryClient;
import com.micro.order_service.model.Order;
import com.micro.order_service.repo.OrderRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private OrderRepo orderRepo;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String,OrderEvent> kafkaTemplate;
    
    public void placeOrder(Order order){
        var isInStock = inventoryClient.isInStock(order.getSkuCode(), order.getQuantity());
        if(isInStock){
            order.setId(null);
            order.setOrderNumber(UUID.randomUUID().toString());

            orderRepo.save(order);

            OrderEvent oe = new OrderEvent();
            oe.setEmail(order.getEmail());
            oe.setOrderNumber(order.getOrderNumber());
            kafkaTemplate.send("order-placed",oe);
            log.info("Message sent - " + oe.toString());
        }else{
            throw new RuntimeException("Product with skucode " + order.getSkuCode()+" is not in stock.");
        }

    }
}
