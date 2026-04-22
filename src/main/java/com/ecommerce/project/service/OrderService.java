package com.ecommerce.project.service;

import com.ecommerce.project.entity.*;
import com.ecommerce.project.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    /**
     * ✅ Place Order
     */
    @Transactional
    public Order placeOrder(Long userId, Long addressId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);

        // ✅ FIX HERE
        BigDecimal totalAmount = cart.getItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalPrice(totalAmount);

        orderRepository.save(order);

        return order;
    }
    /**
     * ✅ Get all orders of a user
     */
    public List<Order> getUserOrders(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        return orderRepository.findByUserId(userId);
    }

    /**
     * ✅ Get single order
     */
    public Order getSingleOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    /**
     * ✅ Cancel Order (with stock restore)
     */
    @Transactional
    public void cancelOrder(Long orderId) {

        Order order = getSingleOrder(orderId);

        // 🔥 Prevent invalid cancellations
        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new RuntimeException("Delivered order cannot be cancelled");
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Order already cancelled");
        }

        // 🔹 Restore stock
        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            product.setStockQuantity(
                    product.getStockQuantity() + item.getQuantity()
            );
            productRepository.save(product);
        }

        // 🔹 Update status
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
}