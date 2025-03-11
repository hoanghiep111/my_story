package com.shop.my.controller;

import com.shop.my.entity.Order;
import com.shop.my.entity.User;
import com.shop.my.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // API để checkout giỏ hàng
    @PostMapping("/checkout")
    public Order checkout() {
        User user = getCurrentUser(); // Lấy user từ session hoặc token
        return orderService.checkout(user);
    }

    // Giả lập lấy user từ session/token
    private User getCurrentUser() {
        User user = new User();
        user.setId(1L); // Giả định user có ID = 1
        return user;
    }

}
