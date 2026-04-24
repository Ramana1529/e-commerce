package com.ecommerce.project.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

import java.math.BigDecimal;
@Builder
@Data
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String categoryName;
    private String imageUrl;
    private Boolean inStock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
