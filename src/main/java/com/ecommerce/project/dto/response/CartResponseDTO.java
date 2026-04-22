package com.ecommerce.project.dto.response;

import com.ecommerce.project.dto.response.CartItemResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Builder
@Data
public class CartResponseDTO {
    private Long id;
    private List<CartItemResponseDTO> items;
    private BigDecimal totalAmount;
}