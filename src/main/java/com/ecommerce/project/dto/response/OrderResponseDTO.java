package com.ecommerce.project.dto.response;

import com.ecommerce.project.dto.response.OrderItemResponseDTO;
import com.ecommerce.project.entity.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Builder
@Data
public class OrderResponseDTO {
    private Long orderId;
    private LocalDateTime orderDate;
    private String status;
    private BigDecimal totalAmount;
    private List<OrderItemResponseDTO> items;
    private Long addressId;
    private String paymentStatus;
}
