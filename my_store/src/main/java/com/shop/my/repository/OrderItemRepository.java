package com.shop.my.repository;

import com.shop.my.entity.Order;
import com.shop.my.entity.OrderItems;
import com.shop.my.entity.User;
import com.shop.my.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItems,Long> {
    // Find a list of orders of a user with a specific status
    List<Order> findByUserAndOrder_OrderStatus(User user, OrderStatus orderStatus);
}
