package com.ecommerce.project.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponseDTO {
    private Long id;
    private String name;
}
