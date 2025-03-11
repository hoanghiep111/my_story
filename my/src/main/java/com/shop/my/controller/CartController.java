package com.shop.my.controller;


import com.shop.my.dto.request.cart.CartRequest;
import com.shop.my.entity.Cart;
import com.shop.my.entity.CartItems;
import com.shop.my.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable Long userId) {
        return cartService.getCartByUser(userId);
    }


    @PostMapping("/{userId}/add")
    public String addToCart(@PathVariable Long userId, @RequestBody CartRequest request) {
        cartService.addProductCart(userId, request);
        return "Sản phẩm đã được thêm vào giỏ hàng!";
    }

    @DeleteMapping("/{userId}/remove/{productId}")
    public String removeFromCart(@PathVariable Long userId, @PathVariable Long productId) {
        cartService.removeProductInCart(userId, productId);
        return "Sản phẩm đã được xóa khỏi giỏ hàng!";
    }

    @GetMapping("/{userId}/items")
    public Set<CartItems> getCartItems(@PathVariable Long userId) {
        return cartService.getCartItems(userId);
    }

    @DeleteMapping("/{userId}/clear")
    public String clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return "All products have been removed from the cart!";
    }



}
