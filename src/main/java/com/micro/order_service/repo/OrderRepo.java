package com.micro.order_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.micro.order_service.model.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long>{
    
}
