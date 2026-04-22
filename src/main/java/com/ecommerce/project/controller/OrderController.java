package com.ecommerce.project.controller;

import com.ecommerce.project.entity.Order;
import com.ecommerce.project.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // ✅ Place Order
    @PostMapping("/place")
    public Order placeOrder(@RequestParam Long userId,
                            @RequestParam Long addressId) {
        return orderService.placeOrder(userId, addressId);
    }

    // ✅ Get all orders of user
    @GetMapping("/user/{userId}")
    public List<Order> getAllOrders(@PathVariable Long userId) {
        return orderService.getUserOrders(userId);
    }

    // ✅ Get single order
    @GetMapping("/{orderId}")
    public Order getSingleOrder(@PathVariable Long orderId) {
        return orderService.getSingleOrder(orderId);
    }

    // ✅ NEW: Cancel Order
    @DeleteMapping("/{orderId}/cancel")
    public String cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return "Order cancelled successfully";
    }
}