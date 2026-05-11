package com.ecommerce.project.service;

import com.ecommerce.project.dto.response.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    OrderResponseDTO placeOrder(Long addressId);
    List<OrderResponseDTO> getUserOrders();
    OrderResponseDTO getSingleOrder(Long orderId);
    void cancelOrder(Long orderId);
}
