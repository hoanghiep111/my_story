package com.shop.my.controller;

import com.shop.my.dto.request.category.CategoryCreateRequest;
import com.shop.my.dto.request.category.CategoryUpdate;
import com.shop.my.dto.request.product.ProductCreateRequest;
import com.shop.my.dto.request.product.ProductUpdateRequest;
import com.shop.my.entity.Category;
import com.shop.my.entity.Product;
import com.shop.my.service.CategoryService;
import com.shop.my.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    Product createProduct(@RequestBody ProductCreateRequest request){
        return productService.createProduct(request);
    }

    @GetMapping
    List<Product> getProduct(){
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    Product getProduct(@PathVariable("productId") Long productId){
        return productService.getProductById(productId);
    }

    @PutMapping("/{productId}")
    Product updateProdcut(@PathVariable Long productId, @RequestBody ProductUpdateRequest request){
        return  productService.updateProduct(productId,request);

    }

    @DeleteMapping("/{productId}")
    String deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
        return  "Catrgory has been delete";
    }
}
