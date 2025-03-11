package com.shop.my.controller;

import com.shop.my.dto.request.category.CategoryCreateRequest;
import com.shop.my.dto.request.category.CategoryUpdate;
import com.shop.my.dto.request.user.UserUpdateRequest;
import com.shop.my.entity.Category;
import com.shop.my.entity.User;
import com.shop.my.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    Category createCategory(@RequestBody CategoryCreateRequest request){
        return categoryService.createCategory(request);
    }

    @GetMapping
    List<Category> getCategory(){
        return categoryService.getCategory();
    }

    @GetMapping("/{categoryId}")
    Category getCategory(@PathVariable("categoryId") Long categoryId){
        return categoryService.getCategory(categoryId);
    }

    @PutMapping("/{categoryId}")
    Category updateCategory(@PathVariable Long categoryId, @RequestBody CategoryUpdate request){
        return  categoryService.updateCategory(categoryId,request);

    }

    @DeleteMapping("/{categoryId}")
    String deleteCategory(@PathVariable Long categoryId){
        categoryService.deleteCategory(categoryId);
        return  "Catrgory has been delete";
    }


}
