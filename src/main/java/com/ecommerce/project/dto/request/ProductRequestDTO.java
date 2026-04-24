package com.ecommerce.project.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDTO {

    @NotBlank(message = "Product name cannot be empty")
    private String name;

    @NotBlank(message = "Description cannot be empty")
    @Size(max = 500)
    private String description;

    @NotNull(message = "Price is required")
    @Positive
    private BigDecimal price;

    @NotNull(message = "Stock is required")
    @Min(value = 0)
    private Integer stockQuantity;

    @NotNull(message = "Category ID is required")
    private Long categoryId;
}