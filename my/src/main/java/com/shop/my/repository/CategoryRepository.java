package com.shop.my.repository;

import com.shop.my.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT MAX(c.categoryCode) FROM Category c WHERE c.categoryCode LIKE '____'")
    String findMaxCategoryCode();
}
