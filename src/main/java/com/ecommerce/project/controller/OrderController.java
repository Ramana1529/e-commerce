package com.ecommerce.project.controller;

import com.ecommerce.project.dto.request.OrderRequestDTO;
import com.ecommerce.project.dto.response.OrderResponseDTO;
import com.ecommerce.project.entity.Order;
import com.ecommerce.project.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public OrderResponseDTO placeOrder(@RequestBody @Valid OrderRequestDTO request) {
        return orderService.placeOrder(request.getAddressId());
    }
    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<OrderResponseDTO> getUserOrders() {
        return orderService.getUserOrders();
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN') or @orderSecurity.isOwner(#orderId, authentication)")
    public OrderResponseDTO getSingleOrder(@PathVariable Long orderId) {
        return orderService.getSingleOrder(orderId);
    }

    @DeleteMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN') or @orderSecurity.isOwner(#orderId, authentication)")
    public String cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return "Order cancelled successfully";
    }
}