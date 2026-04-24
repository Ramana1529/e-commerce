package com.ecommerce.project.mapper;

import com.ecommerce.project.dto.response.OrderItemResponseDTO;
import com.ecommerce.project.dto.response.OrderResponseDTO;
import com.ecommerce.project.entity.OrderItem;
import com.ecommerce.project.entity.Order;
import com.ecommerce.project.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {
    public static OrderResponseDTO toDto(Order order){
        if(order==null)return null;
        List<OrderItemResponseDTO> items = order.getOrderItems()
                .stream()
                .map(OrderMapper::mapItem)
                .toList();
        return OrderResponseDTO.builder()
                .orderId(order.getId())
                .orderDate(order.getOrderDate())
                .status(order.getStatus()!=null ? order.getStatus().name():null)
                .items(items)
                .totalAmount(totalAmount(order))
                .addressId(order.getAddress() != null ? order.getAddress().getId() : null)
                .paymentStatus(order.getPayment()!=null && order.getPayment().getPaymentStatus()!=null ? order.getPayment().getPaymentStatus().name():null)
                .build();
    }
    public static OrderItemResponseDTO mapItem(OrderItem orderItem){
        if (orderItem == null) return null;
        BigDecimal totalPrice = orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
        return OrderItemResponseDTO.builder()
                .productId(orderItem.getProduct() != null ? orderItem.getProduct().getId() : null)
                .productName(orderItem.getProduct() != null ? orderItem.getProduct().getName() : null)
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .totalPrice(totalPrice)
                .build();
    }
    public static BigDecimal totalAmount(Order order){
        if (order.getOrderItems() == null) return BigDecimal.ZERO;
        return order.getOrderItems().stream()
                .map(item->item.getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }
}
