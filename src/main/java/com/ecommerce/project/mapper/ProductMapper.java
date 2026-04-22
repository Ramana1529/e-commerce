package com.ecommerce.project.mapper;

import com.ecommerce.project.dto.request.ProductRequestDTO;
import com.ecommerce.project.dto.response.ProductResponseDTO;
import com.ecommerce.project.entity.Category;
import com.ecommerce.project.entity.Product;

import java.time.LocalDateTime;

public class ProductMapper {
    public static Product toEntity(ProductRequestDTO dto, Category category){
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stockQuantity(dto.getStockQuantity())
                .category(category)
                .build();
    }
    public static ProductResponseDTO toDto(Product product){
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .stockQuantity(product.getStockQuantity())
                .price(product.getPrice())
                .categoryName(product.getCategory().getName())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
