package com.shop.my.repository;

import com.shop.my.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems, Long> {
        // find a cart item based on the cart ID  and product ID
        Optional<CartItems> findByCartIdAndProductId(Long cartId, Long productId);
}
