package com.shop.my.service;

import com.shop.my.dto.request.product.ProductCreateRequest;
import com.shop.my.dto.request.product.ProductUpdateRequest;
import com.shop.my.entity.Category;
import com.shop.my.entity.Product;
import com.shop.my.repository.CategoryRepository;
import com.shop.my.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Product createProduct(ProductCreateRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setImageUrl(request.getImageUrl());


        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);

        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductUpdateRequest request) {
        Product product = getProductById(id);
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setImageUrl(request.getImageUrl());

        // Cập nhật Category nếu có
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);

        product.setUpdatedAt(LocalDateTime.now());

        return productRepository.save(product);
    }

    public void deleteProduct(Long prodcutId) {
        productRepository.deleteById(prodcutId);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    private String generateProductCode(Category category) {
        String categoryCode = category.getCategoryCode(); // Giả sử Category có trường `code`
        Long count = productRepository.countByCategory(category); // Đếm số lượng sản phẩm trong category

        String formattedNumber = String.format("%06d", count + 1);
        return categoryCode + "_" + formattedNumber;
    }


}
