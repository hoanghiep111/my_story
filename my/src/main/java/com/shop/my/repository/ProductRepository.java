package com.shop.my.repository;

import com.shop.my.entity.Category;
import com.shop.my.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProductRepository extends JpaRepository<Product, Long> {
    Long countByCategory(Category category);
}
