package com.shop.my.repository;

import com.shop.my.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    //Helps find the cart based on the user's userId
    Optional<Cart> findByUserId(Long userId);
}
