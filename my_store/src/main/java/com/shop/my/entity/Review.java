package com.shop.my.entity;

import java.security.Timestamp;

public class Review {
    private Long id;
    private User user;
    private Product product;
    private int rating;
    private String comment;
    private Timestamp createdAt;
}
