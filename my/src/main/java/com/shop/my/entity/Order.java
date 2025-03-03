package com.shop.my.entity;

import java.security.Timestamp;

public class Order {
    private Long id;
    private User user;
    private double totalPrice;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
