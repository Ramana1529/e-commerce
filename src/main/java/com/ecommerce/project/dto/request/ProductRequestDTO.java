package com.ecommerce.project.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Builder
public class ProductRequestDTO {
    @NotBlank(message = "Product name cannot be empty")
    private String name;
    @NotBlank(message = "Description cannot be empty")
    @Size(max = 500, message = "Description too long")
    private String description;
    @NotNull(message = "Price is required")
    @Positive
    private BigDecimal price;
    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stockQuantity;
    @NotNull(message = "Category ID is required")
    private Long categoryId;
}
