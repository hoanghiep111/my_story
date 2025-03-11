package com.shop.my.service;

import com.shop.my.dto.request.category.CategoryCreateRequest;
import com.shop.my.dto.request.category.CategoryUpdate;
import com.shop.my.dto.request.user.UserUpdateRequest;
import com.shop.my.entity.Category;
import com.shop.my.entity.User;
import com.shop.my.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;


    private String generateCategoryCode() {
        //Lấy mã lớn nhất hiện có trong DB" rồi +1 để tạo mã mới.
        String lastCode = categoryRepository.findMaxCategoryCode();
        int nextNumber;
        if (lastCode == null) {
            nextNumber = 1;
        } else {
            nextNumber = Integer.parseInt(lastCode) + 1;
        }
        return String.format("%04d", nextNumber);
    }

    public Category createCategory(CategoryCreateRequest request) {

        Category category = new Category();

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setStatus(request.getStatus());
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        return categoryRepository.save(category);
    }


    public Category updateCategory(Long categoryId, CategoryUpdate request) {
        Category category = getCategory(categoryId);
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setStatus(request.getStatus());
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        return categoryRepository.save(category);
    }


    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    public List<Category> getCategory() {
        return categoryRepository.findAll();

    }

    public Category getCategory(Long id) {
        return categoryRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}