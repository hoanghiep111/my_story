package com.shop.my.entity;

import jdk.jfr.Category;

import java.security.Timestamp;

public class Product {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;
    private String imageUrl;
    private Category category;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
