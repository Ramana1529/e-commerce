package com.ecommerce.project.mapper;

import com.ecommerce.project.dto.response.OrderItemResponseDTO;
import com.ecommerce.project.dto.response.OrderResponseDTO;
import com.ecommerce.project.entity.OrderItem;
import com.ecommerce.project.entity.Order;
import com.ecommerce.project.entity.OrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {
    public static OrderResponseDTO toDto(Order order){
        return OrderResponseDTO.builder()
                .orderId(order.getId())
                .orderDate(order.getOrderDate())
                .addressId(order.getAddress() != null ? order.getAddress().getId() : null)
                .status(order.getStatus())
                .items(order.getOrderItems().stream()
                        .map(OrderMapper::mapItem)
                        .collect(Collectors.toList()))
                .totalAmount(totalAmount(order))
                .build();
    }
    public static OrderItemResponseDTO mapItem(OrderItem orderItem){
        BigDecimal totalPrice = orderItem.getPrice()
                .multiply(BigDecimal.valueOf(orderItem.getQuantity()));
        return OrderItemResponseDTO.builder()
                .productId(orderItem.getProduct().getId())
                .productName(orderItem.getProduct().getName())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .totalPrice(totalPrice)
                .build();
    }
    public static BigDecimal totalAmount(Order order){
        return order.getOrderItems().stream()
                .map(items->items.getPrice()
                        .multiply(BigDecimal.valueOf(items.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }
}
