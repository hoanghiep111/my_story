package com.shop.my.service;

import com.shop.my.entity.*;
import com.shop.my.enums.OrderStatus;
import com.shop.my.enums.PaymentMethod;
import com.shop.my.enums.PaymentStatus;
import com.shop.my.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemsRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final PaymentRepository paymentRepository;


    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemsRepository,
                        CartRepository cartRepository, CartItemRepository cartItemRepository, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.orderItemsRepository = orderItemsRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.paymentRepository = paymentRepository;
    }

    // Checkout từ giỏ hàng
    public Order checkout(User user) {
        // Lấy giỏ hàng của user
        Cart cart = cartRepository.findById(user.getId()).orElse(null);
        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty, can't order!");
        }

        // Create new order
        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.Paid);
        order.setTotalPrice(cart.getTotalPrice());
        order = orderRepository.save(order);

        // Move products from cart to order
        for (CartItems cartItem : cart.getCartItems()) {
            OrderItems orderItem = new OrderItems();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItemsRepository.save(orderItem);
        }

        // Clear cart after successful order
        cartItemRepository.deleteAll(cart.getCartItems());
        cartRepository.delete(cart);

        return order;
    }

    public Payment payOrder(Long orderId, User user, PaymentMethod method) {
        // Find orders to pay
        Order order = orderRepository.findById(orderId)
                .filter(o -> o.getUser().equals(user))
                .orElseThrow(() -> new RuntimeException("Order not found!"));

        // Check if order has been paid
        if (order.getOrderStatus() == OrderStatus.Paid) {
            throw new RuntimeException("Order has been paid!");
        }

        // Create payment object
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod(method);
        payment.setAmount(order.getTotalPrice());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus(PaymentStatus.Paid);

        paymentRepository.save(payment);

        // Update order status
        order.setOrderStatus(OrderStatus.Paid);
        orderRepository.save(order);

        return payment;
    }

    public List<Order> getUserOrderHistory(User user) {
        return orderRepository.findByUser(user);
    }



}
