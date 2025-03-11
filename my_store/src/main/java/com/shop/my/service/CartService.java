package com.shop.my.service;

import com.shop.my.dto.request.cart.CartRequest;
import com.shop.my.entity.Cart;
import com.shop.my.entity.CartItems;
import com.shop.my.entity.Product;
import com.shop.my.entity.User;
import com.shop.my.repository.CartItemRepository;
import com.shop.my.repository.CartRepository;
import com.shop.my.repository.ProductRepository;
import com.shop.my.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    //This method finds the shopping cart (Cart) of a user based on userId.
    public Cart getCartByUser(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException(" The cart does not exist."));
    }


    public void addProductCart(Long userId, CartRequest request) {
        // check if user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(" User does not exist"));

        //Check if the product exists.
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product does not exist"));

        // get user's shopping cart (create new cart if not exist)
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);  //Assign the user to the cart
                    newCart.setTotalPrice(0.0); //Set the total price (totalPrice) of the cart to 0.0 (initially, there are no products)
                    return cartRepository.save(newCart);
                });

        // Check if the product is in the cart
        Optional<CartItems> foundProductInCart = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        CartItems cartItem = new CartItems();
        if (foundProductInCart.isPresent()) {
            // Update quantity if product exists
            foundProductInCart.get();
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
            cartItem.updateTotalPrice();
            cartItemRepository.save(cartItem);
        } else {
            // Add new product to cart

            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.updateTotalPrice();
            cart.getCartItems().add(cartItem);
            cartItemRepository.save(cartItem);
        }

        cartItemRepository.save(cartItem);

        // Update cart total price
        cart.updateTotalPrice();
        cartRepository.save(cart);
    }


    public void removeProductInCart(Long userId, Long productId) {
        Cart cart = getCartByUser(userId);  // Find the user's cart based on userId
        CartItems item = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new RuntimeException("Product does not exist in cart"));

        cartItemRepository.delete(item);
        cart.updateTotalPrice();
        cartRepository.save(cart);
    }

    //Get a list of all products in a user's shopping cart
    public Set<CartItems> getCartItems(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart does not exist"));
        return cart.getCartItems();
    }

    // delete all prodcut in cart
    public void clearCart(Long userId) {
        Cart cart = getCartByUser(userId);
        cartItemRepository.deleteAll(cart.getCartItems());
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
    }



}
