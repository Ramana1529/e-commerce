package com.ecommerce.project.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Builder
@Data
public class OrderItemResponseDTO {
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
}
